package com.mylib.core.repositories;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mylib.core.entities.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
	public Book getByTitle(String title) {
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
	
	@Transactional
	public List<Book> getBooksByAuthor(String authorName){
		String queryString = """
				SELECT b FROM Book b 
				INNER JOIN FETCH b.author a
				WHERE a.fullName = :authorName""";
		TypedQuery<Book> typedQuery = this.entityManager.createQuery(queryString,Book.class);
		typedQuery.setParameter("authorName", authorName);
		List<Book> books = typedQuery.getResultList();
		return books;
	}
	
	@Transactional
	public List<Book> getAll() {
		String queryString = """
				SELECT b FROM Book b 
				INNER JOIN FETCH b.author 
				INNER JOIN FETCH b.illustrator 
				INNER JOIN FETCH b.editor  
				INNER JOIN FETCH b.collection""";
		List<Book> books = this.entityManager.createQuery(queryString,Book.class).getResultList();
		return books;
	}
	
	@Transactional
	public void updateBook(Book book) {
		this.entityManager.merge(book);
	}
	
	@Transactional
	public void deleteBook(long id) {
		Book book = getById(id);
		this.entityManager.remove(book);
	}
	
	@Transactional
	public void deleteAllFromDatabase() {
		System.out.println("La méthode deleteAllFromDatabase a été appelée");
		String queryStringBook = "DELETE FROM Book b";
		String queryStringAuthor = "DELETE FROM Author a";
		String queryStringIllustrator = "DELETE FROM Illustrator i";
		String queryStringEditor = "DELETE FROM Editor e";
		String queryStringCollection = "DELETE FROM Collection c";
		Query queryBook  = this.entityManager.createQuery(queryStringBook);
		queryBook.executeUpdate();
		Query queryAuthor  = this.entityManager.createQuery(queryStringAuthor);
		queryAuthor.executeUpdate();
		Query queryIllustrator  = this.entityManager.createQuery(queryStringIllustrator);
		queryIllustrator.executeUpdate();
		Query queryEditor  = this.entityManager.createQuery(queryStringEditor);
		queryEditor.executeUpdate();
		Query queryCollection  = this.entityManager.createQuery(queryStringCollection);
		queryCollection.executeUpdate();
	}
	
	//methode qui retourne une liste de livres par autheur, editeur, collection
	
	
	
	

}
