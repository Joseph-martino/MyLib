package com.mylib.core.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Collection;
import com.mylib.core.enums.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class CustomizedCollectionRepositoryImpl implements CustomizedCollectionRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createCollection(Collection collection) {
		this.entityManager.persist(collection);
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
		Query query = this.entityManager.createQuery("DELETE FROM Collection c WHERE status =:status");
		query.setParameter("status", Status.OK.toString());
		query.executeUpdate();	
	}
}
