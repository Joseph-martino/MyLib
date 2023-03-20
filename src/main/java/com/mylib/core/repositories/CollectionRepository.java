package com.mylib.core.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Collection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CollectionRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createCollection(Collection collection) {
		this.entityManager.persist(collection);
		
		System.out.println("La collection a été enregistrée");	
	}

}
