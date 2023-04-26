package com.mylib.core.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Collection;
import com.mylib.core.entities.Editor;
import com.mylib.core.enums.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class CustomizedCollectionRepositoryImpl implements CustomizedCollectionRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createCollection(Collection collection) {
		this.entityManager.persist(collection);
	}
	
	@Transactional
	public Collection getCollectionByNameAndStatus(String name) {
		String queryString  = "FROM Collection c WHERE name=:name AND status=:status";
		TypedQuery<Collection> typedQuery = this.entityManager.createQuery(queryString, Collection.class);
		typedQuery.setParameter("name", name);
		typedQuery.setParameter("status", Status.IN_PROGRESS.toString());
		List<Collection> collections = typedQuery.getResultList();
		
		if(collections.isEmpty()) {
			return null;
		} else {
			return collections.get(0);
		}
		
		//return typedQuery.getSingleResult(); //prendre la methode getResulltList et si la liste rettournée est vide on renvoie null sinon on renvoie le premier resultat de la liste
	}
	
	/**
	 * Met à jour le status IN_PROGRESS en OK
	 */
	@Transactional
	public void changeCollectionStatusToOk() {
		Query query = this.entityManager.createQuery("UPDATE Collection c SET status = :status WHERE status= :inProgress");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("inProgress", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
		
	/**
	 * Met à jour le status TO_DELETE en OK
	 */
	@Transactional
	public void changeCollectionWithStatusToDeleteToOk() {
		Query query = this.entityManager.createQuery("UPDATE Collection c SET status = :status WHERE status= :toDelete");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("toDelete", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Met à jour le status OK en TO_DELETE
	 */
	@Transactional
	public void changeCollectionStatusToToDelete() {
		Query query = this.entityManager.createQuery("UPDATE Collection c SET status = :status WHERE status =:ok");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.setParameter("ok", Status.OK.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status TO_DELETE
	 */
	@Transactional
	public void deleteCollectionWithStatusToDelete() {
		Query query = this.entityManager.createQuery("DELETE FROM Collection c WHERE status =:status");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status IN PROGRESS
	 */
	@Transactional
	public void deleteCollectionWithStatusInProgress() {
		Query query = this.entityManager.createQuery("DELETE FROM Collection c WHERE status =:status");
		query.setParameter("status", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status OK
	 */
	@Transactional
	public void deleteCollectionWithStatusOk() {
		Query query = this.entityManager.createQuery("DELETE FROM Collection c WHERE status = :status");
		query.setParameter("status", Status.OK.toString());
		query.executeUpdate();	
	}
}
