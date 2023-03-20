package com.mylib.core.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mylib.core.entities.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BookRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createBook(Book book) {
		this.entityManager.persist(book);
		
		System.out.println("Le livre a été enregistré");
		
		
		
	}

}
