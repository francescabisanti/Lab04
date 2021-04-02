package it.polito.tdp.lab04;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> ChoiceCorsi;

    @FXML
    private Button btmCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btmOK;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btmCercaCorso;

    @FXML
    private Button btmIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btmReset;

    @FXML
    void doCercaCorso(ActionEvent event) {
    	String inserito= this.txtMatricola.getText();
    	int matricola;
    	try {
    		matricola=Integer.parseInt(inserito);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un numero!");
    		e.printStackTrace();
    		return;
    	}
    	
    	if(inserito.length()!=6) {
    		this.txtResult.setText("Inserisci la matricola giusta!");
    		return;
    	}
    	String result="";
    	Studente s= this.model.getTuttiGliStudenti().get(matricola);
    	this.txtResult.setStyle("-fx-font-family: monospace");
    	if(s!=null) {
    	for(Corso c: this.model.getTuttiICorsiSingoloStudente(s)) {
    		result=result+String.format("%-8s", c.getCodins())+String.format("%-50s",c.getNome())+String.format("%-2s", c.getCrediti())+String.format("%-2s", c.getPd())+"\n";
    		
    	}
    	
    		this.txtResult.setText(result);
    }	else {
    	this.txtResult.setText("Studente non trovato, inserisci una matricola dall'elenco!");
    	return;
    }
    	}

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	String nomeCorso= this.ChoiceCorsi.getValue();
    	if(nomeCorso==null) {
    		this.txtResult.setText("Seleziona un corso!");
    		return;
    	}
    	Corso corsoPassato=null;
    	for(Corso c: this.model.getTuttiICorsi()) {
    		if(c.getNome().equals(nomeCorso)) {
    			corsoPassato=c;
    		}
    	}
    this.model.getCorsoDao().getStudentiIscrittiAlCorso(corsoPassato);
    for(Studente s: this.model.getCorsoDao().getStudentiPerCorso()) {
    	this.txtResult.appendText(s.getMatricola()+"\t"+s.getCognome()+"\t"+s.getNome()+"\t"+s.getCDS()+"\n");
    
    }
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	String nomeCorso= this.ChoiceCorsi.getValue();
    	if(nomeCorso==null) {
    		this.txtResult.setText("Seleziona un corso!");
    		return;
    	}
    	Corso corsoPassato=null;
    	for(Corso c: this.model.getTuttiICorsi()) {
    		if(c.getNome().equals(nomeCorso)) {
    			corsoPassato=c;
    		}
    	}
    List<Studente>controllo=new LinkedList<Studente>();
    this.model.getCorsoDao().getStudentiIscrittiAlCorso(corsoPassato);
    controllo=this.model.getCorsoDao().getStudentiPerCorso();
    String inserito= this.txtMatricola.getText();
	int matricola;
	try {
		matricola=Integer.parseInt(inserito);
	}catch(NumberFormatException e) {
		this.txtResult.setText("Devi inserire un numero!");
		e.printStackTrace();
		return;
	}
	
	if(inserito.length()!=6) {
		this.txtResult.setText("Inserisci la matricola giusta!");
		return;
	}
	Studente con=null;
	boolean esiste=false;
	for(Studente ss: this.model.getTuttiGliStudenti().values()) {
		if(ss.getMatricola()==matricola) {
			esiste=true;
			con=ss;
		}
	}
	if(!esiste) {
		this.txtResult.setText("Matricola inesistente!");
		return;
	}
	 this.txtNome.setText(con.getNome());
	 this.txtCognome.setText(con.getCognome());
	boolean trovato=false;
	for(Studente s: controllo) {
		if(s.getMatricola()==matricola) {
			trovato=true;
		}
	}
	if(trovato==true) {
		this.txtResult.setText("Studente già iscritto a questo corso");
		return;
	}
	if(!this.model.inscriviStudenteACorso(con, corsoPassato)) {
		txtResult.appendText("Errore durante l'iscrizione al corso");
		return;
	}else {
		txtResult.appendText("Studente iscritto al corso");
	}
   
    }

    @FXML
    void doOk(ActionEvent event) {
    	String inserito= this.txtMatricola.getText();
    	int matricola;
    	try {
    		matricola=Integer.parseInt(inserito);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un numero!");
    		e.printStackTrace();
    		return;
    	}
    	if(inserito.length()!=6) {
    		this.txtResult.setText("Inserisci la matricola giusta!");
    		return;
    	}
    	Studente s= this.model.getTuttiGliStudenti().get(matricola);
    	this.txtNome.setText(s.getNome());
    	this.txtCognome.setText(s.getCognome());
    }

    @FXML
    void doReset(ActionEvent event) {
    	this.txtResult.clear();

    }

    @FXML
    void initialize() {
        assert ChoiceCorsi != null : "fx:id=\"ChoiceCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmCercaIscritti != null : "fx:id=\"btmCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmOK != null : "fx:id=\"btmOK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmCercaCorso != null : "fx:id=\"btmCercaCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmIscrivi != null : "fx:id=\"btmIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmReset != null : "fx:id=\"btmReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	for(Corso c: this.model.getTuttiICorsi()) {
    		this.ChoiceCorsi.getItems().add(c.getNome());
    	}
    	this.ChoiceCorsi.getItems().add("Altro...");
    	
    }
    
}
