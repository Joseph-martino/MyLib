package com.mylib.core.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Author;
import com.mylib.core.entities.Book;
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
	
	@Transactional
	public Collection getCollectionByName(String name) {
		Collection collection = this.entityManager.find(Collection.class, name);
		return collection;
	}
	
	@Transactional
	public boolean checkIfCollectionNameExist(String name) {
		Collection collection = getCollectionByName(name);
		if(collection.getName().equals(name)) {
			return true;
		} else {
			return false;
		}
	}

}
