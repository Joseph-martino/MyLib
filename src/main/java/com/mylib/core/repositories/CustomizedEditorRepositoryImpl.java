package com.mylib.core.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.enums.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class CustomizedEditorRepositoryImpl implements CustomizedEditorRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Met à jour le status IN_PROGRESS en OK
	 */
	@Transactional
	public void changeEditorStatusToOk() {
		Query query = this.entityManager.createQuery("UPDATE Editor e SET status = :status WHERE status= :inProgress");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("inProgress", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
		
	/**
	 * Met à jour le status TO_DELETE en OK
	 */
	@Transactional
	public void changeEditorWithStatusToDeleteToOk() {
		Query query = this.entityManager.createQuery("UPDATE Editor e SET status = :status WHERE status= :toDelete");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("toDelete", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Met à jour le status OK en TO_DELETE
	 */
	@Transactional
	public void changeEditorStatusToToDelete() {
		Query query = this.entityManager.createQuery("UPDATE Editor e SET status = :status WHERE status =:ok");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.setParameter("ok", Status.OK.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status TO_DELETE
	 */
	@Transactional
	public void deleteEditorWithStatusToDelete() {
		Query query = this.entityManager.createQuery("DELETE FROM Editor e WHERE status =:status");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status IN PROGRESS
	 */
	@Transactional
	public void deleteEditorWithStatusInProgress() {
		Query query = this.entityManager.createQuery("DELETE FROM Editor e WHERE status =:status");
		query.setParameter("status", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status OK
	 */
	@Transactional
	public void deleteEditorWithStatusOk() {
		Query query = this.entityManager.createQuery("DELETE FROM Editor e WHERE status =:status");
		query.setParameter("status", Status.OK.toString());
		query.executeUpdate();	
	}
}
