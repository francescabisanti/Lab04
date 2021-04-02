package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	CorsoDAO corsoDao= new CorsoDAO();
	StudenteDAO studenteDao= new StudenteDAO();
	
	public List<Corso> getTuttiICorsi() {
		
		return corsoDao.getTuttiICorsi();
	}
	
	public List <String> getNomeCorsi(List <Corso> corsi){
		List <String> corsiNome= new LinkedList <String>();
		for(Corso c: corsi) {
			corsiNome.add(c.getNome());
		}
		return corsiNome;
	}
	
	public Map<Integer,Studente> getTuttiGliStudenti() {
		return studenteDao.getTuttiGliStudenti();
	}

	public CorsoDAO getCorsoDao() {
		return corsoDao;
	}

	public void setCorsoDao(CorsoDAO corsoDao) {
		this.corsoDao = corsoDao;
	}

	public StudenteDAO getStudenteDao() {
		return studenteDao;
	}

	public void setStudenteDao(StudenteDAO studenteDao) {
		this.studenteDao = studenteDao;
	}
	public List<Corso> getTuttiICorsiSingoloStudente(Studente studente) {
		return studenteDao.getTuttiICorsiSingoloStudente(studente);
	}
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return this.corsoDao.inscriviStudenteACorso(studente, corso);
	}
	
}
