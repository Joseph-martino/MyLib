package com.mylib.core.repositories;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mylib.core.entities.Book;
import com.mylib.core.enums.Status;

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
	public List<Book> getBooksListFromView(String authorName, String illustratorName, String editorName, 
											String collectionName, int pageNumber, int pageSize) {
		String queryString = "FROM Book b WHERE 1=1";
		Map<String, String> queryKeysValues = new HashMap<>();
		if(authorName != null) {
			queryString = queryString + " AND b.author.fullName = :authorName";
			queryKeysValues.put("authorName", authorName);
		}
		
		if(illustratorName != null) {
			queryString = queryString + " AND b.illustrator.fullName = :illustratorName";
			queryKeysValues.put("illustratorName", illustratorName);
		}
		
		if(editorName != null) {
			queryString = queryString + " AND b.editor.name = :editorName";
			queryKeysValues.put("editorName", editorName);
		}
		
		if(collectionName != null) {
			queryString = queryString + " AND b.collection.name = :collectionName";	
			queryKeysValues.put("collectionName", collectionName);
		}
		
		queryString = queryString + "ORDER BY b.id ASC";
		
		TypedQuery<Book> typedQuery = this.entityManager.createQuery(queryString,Book.class);
		for(Map.Entry<String, String> setKeyValue: queryKeysValues.entrySet()) {
			typedQuery.setParameter(setKeyValue.getKey(), setKeyValue.getValue());
		}
//		int pageNumber = 1;
//		int pageSize = 10;
//		query.setFirstResult((pageNumber-1) * pageSize); 
//		query.setMaxResults(pageSize);
		typedQuery.setFirstResult(pageNumber * pageSize + 1);
		typedQuery.setMaxResults(pageSize);
//		typedQuery.setParameter("authorName", authorName);
//		typedQuery.setParameter("illustratorName", illustratorName);
//		typedQuery.setParameter("editorName", editorName);
//		typedQuery.setParameter("collectionName", collectionName);
		List<Book> books = typedQuery.getResultList();
		return books;
	}
	
	@Transactional
	public long getCountData() {
		String queryString = "SELECT COUNT(b) FROM Book b";
		Query query = this.entityManager.createQuery(queryString);
		long numberOfEntries = (long) query.getSingleResult();
		return numberOfEntries;
	}
	
//	@Transactional
//	public List<Book> getBooksByAuthor(String authorName){
//		String queryString = """
//				SELECT b FROM Book b 
//				INNER JOIN FETCH b.author a
//				WHERE a.fullName = :authorName""";
//		TypedQuery<Book> typedQuery = this.entityManager.createQuery(queryString,Book.class);
//		typedQuery.setParameter("authorName", authorName);
//		List<Book> books = typedQuery.getResultList();
//		return books;
//	}
	
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
	
	/**
	 * Met à jour le status IN_PROGRESS en OK
	 */
	@Transactional
	public void changeBookStatusToOk() {
		Query query = this.entityManager.createQuery("UPDATE Book b SET status = :status WHERE status=:inProgress");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("inProgress", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Met à jour le status TO_DELETE en OK
	 */
	@Transactional
	public void changeBookWithStatusToDeleteToOk() {
		Query query = this.entityManager.createQuery("UPDATE Book b SET status = :status WHERE status=:toDelete");
		query.setParameter("status", Status.OK.toString());
		query.setParameter("toDelete", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Met à jour le status OK en TO_DELETE
	 */
	@Transactional
	public void changeBookStatusToToDelete() {
		Query query = this.entityManager.createQuery("UPDATE Book b SET status = :status WHERE status =:ok");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.setParameter("ok", Status.OK.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status TO_DELETE
	 */
	@Transactional
	public void deleteBookWithStatusToDelete() {
		Query query = this.entityManager.createQuery("DELETE FROM Book b WHERE status =:status");
		query.setParameter("status", Status.TO_DELETE.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status IN PROGRESS
	 */
	@Transactional
	public void deleteBookWithStatusInProgress() {
		Query query = this.entityManager.createQuery("DELETE FROM Book b WHERE status =:status");
		query.setParameter("status", Status.IN_PROGRESS.toString());
		query.executeUpdate();	
	}
	
	/**
	 * Supprime les données avec le status OK
	 */
	@Transactional
	public void deleteBookWithStatusOk() {
		Query query = this.entityManager.createQuery("DELETE FROM Book b WHERE status =:status");
		query.setParameter("status", Status.OK.toString());
		query.executeUpdate();	
	}
	
	@Transactional
	public void deleteBook(long id) {
		Book book = getById(id);
		this.entityManager.remove(book);
	}
	
	@Transactional
	public void deleteAllFromDatabase() {
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
}
