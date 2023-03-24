package com.mylib.core.repositories;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mylib.core.entities.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class BookRepository implements IBookRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createBook(Book book) {
		this.entityManager.persist(book);
	}
	
	@Transactional
	public Book getFindByTitle(String title) {
		TypedQuery<Book> typedQuery = this.entityManager.createQuery("FROM Book b WHERE b.title=:title", Book.class);
		typedQuery.setParameter("title", title);
		return typedQuery.getSingleResult();
	}
	
	@Transactional
	public Book getById(long id) {
		return this.entityManager.find(Book.class, id);
	}
	
	
	@Transactional
	public List<Book> getBooksList() {
		String queryString = "FROM Book b";
		List<Book> books = this.entityManager.createQuery(queryString,Book.class).setFirstResult(10).getResultList();
		return books;
	}
	
	//methode qui retourne une liste de livres par autheur, editeur, collection
	
	
	
	

}
