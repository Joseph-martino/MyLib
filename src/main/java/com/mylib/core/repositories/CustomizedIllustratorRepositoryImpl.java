package com.mylib.core.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Author;
import com.mylib.core.entities.Illustrator;
import com.mylib.core.enums.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class CustomizedIllustratorRepositoryImpl implements CustomizedIllustratorRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createIllustrator(Illustrator illustrator) {
		this.entityManager.persist(illustrator);
	}
	
	@Transactional
	public Illustrator getIllustratorByNameAndStatus(String name) {
		String queryString  = "FROM Illustrator i WHERE fullName=:name AND status=:status";
		TypedQuery<Illustrator> typedQuery = this.entityManager.createQuery(queryString, Illustrator.class);
		typedQuery.setParameter("name", name);
		typedQuery.setParameter("status", Status.IN_PROGRESS.toString());
		List<Illustrator> illustrators = typedQuery.getResultList();
		
		if(illustrators.isEmpty()) {
			return null;
		} else {
			return illustrators.get(0);
		}
		
		//return typedQuery.getSingleResult(); //prendre la methode getResulltList et si la liste rettournée est vide on renvoie null sinon on renvoie le premier resultat de la liste
	}
	
	/**
	 * Met à jour le status IN_PROGRESS en OK
	 */
	@Transactional
	public void changeIllustratorStatusToOk() {
		Query query = this.entityManager.createQuery("UPDATE Illustrator i SET status = :status WHERE status= :inProgress");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("inProgress", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
		
	/**
	 * Met à jour le status TO_DELETE en OK
	 */
	@Transactional
	public void changeIllustratorWithStatusToDeleteToOk() {
		Query query = this.entityManager.createQuery("UPDATE Illustrator i SET status = :status WHERE status= :toDelete");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("toDelete", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Met à jour le status OK en TO_DELETE
	 */
	@Transactional
	public void changeIllustratorStatusToToDelete() {
		Query query = this.entityManager.createQuery("UPDATE Illustrator i SET status = :status WHERE status =:ok");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.setParameter("ok", Status.OK.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status TO_DELETE
	 */
	@Transactional
	public void deleteIllustratorWithStatusToDelete() {
		Query query = this.entityManager.createQuery("DELETE FROM Illustrator i WHERE status =:status");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status IN PROGRESS
	 */
	@Transactional
	public void deleteIllustratorWithStatusInProgress() {
		Query query = this.entityManager.createQuery("DELETE FROM Illustrator i WHERE status =:status");
		query.setParameter("status", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status OK
	 */
	@Transactional
	public void deleteIllustratorWithStatusOk() {
		Query query = this.entityManager.createQuery("DELETE FROM Illustrator i WHERE status =:status");
		query.setParameter("status", Status.OK.toString());
		query.executeUpdate();	
	}
}
