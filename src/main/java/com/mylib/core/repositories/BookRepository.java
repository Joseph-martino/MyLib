package com.mylib.core.repositories;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Author;
import com.mylib.core.entities.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class BookRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createBook(Book book) {
		this.entityManager.persist(book);
		
		System.out.println("Le livre a été enregistré");
	}
	
	@Transactional
	public Book getFindByTitle(String title) {
		 Query query = this.entityManager.createQuery("SELECT * FROM Book b WHERE title=?:title")
		.setParameter("title", title);
		
		return (Book) query.getSingleResult();
	}
	
	@Transactional
	public Book getById(long id) {
		return this.entityManager.find(Book.class, id);
	}
	
	
	@Transactional
	public List<Book> getBooksList() {
		String queryString = "SELECT b FROM Book b";
		List<Book> books = this.entityManager.createQuery(queryString,Book.class).setFirstResult(10).getResultList();
		return books;
		
	}
	
	
	
	

}
