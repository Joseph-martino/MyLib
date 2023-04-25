package com.mylib.core.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Author;
import com.mylib.core.enums.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class CustomizedAuthorRepositoryImpl implements CustomizedAuthorRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createAuthor(Author author) {
		this.entityManager.persist(author);
	}
	
	/**
	 * Met à jour le status IN_PROGRESS en OK
	 */
	@Transactional
	public void changeAuthorStatusToOk() {
		Query query = this.entityManager.createQuery("UPDATE Author a SET status = :status WHERE status= :inProgress");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("inProgress", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
		
	/**
	 * Met à jour le status TO_DELETE en OK
	 */
	@Transactional
	public void changeAuthorWithStatusToDeleteToOk() {
		Query query = this.entityManager.createQuery("UPDATE Author a SET status = :status WHERE status= :toDelete");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("toDelete", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Met à jour le status OK en TO_DELETE
	 */
	@Transactional
	public void changeAuthorStatusToToDelete() {
		Query query = this.entityManager.createQuery("UPDATE Author a SET status = :status WHERE status =:ok");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.setParameter("ok", Status.OK.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status TO_DELETE
	 */
	@Transactional
	public void deleteAuthorWithStatusToDelete() {
		Query query = this.entityManager.createQuery("DELETE FROM Author a WHERE status =:status");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status IN PROGRESS
	 */
	@Transactional
	public void deleteAuthorWithStatusInProgress() {
		Query query = this.entityManager.createQuery("DELETE FROM Author a WHERE status =:status");
		query.setParameter("status", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status OK
	 */
	@Transactional
	public void deleteAuthorWithStatusOk() {
		Query query = this.entityManager.createQuery("DELETE FROM Author a WHERE status =:status");
		query.setParameter("status", Status.OK.toString());
		query.executeUpdate();	
	}
}
