package com.mylib.core.repositories;

import java.util.List;

import com.mylib.core.entities.Book;

public interface IBookRepository {
	
	void createBook(Book book);
	List<Book> getBooksList();
	Book getById(long id);
	void deleteBook(long id);
	void updateBook(Book book);
	void deleteAllFromDatabase();
	List<Book> getAll();
	//List<Book> getBooksByAuthor(String authorName);
	List<Book> getBooksListFromView(String authorName, String illustratorName, String editorNamer, String collectionName, int pageNumber, int pageSize);
	void changeBookStatusToOk();
	void changeBookStatusToToDelete();
	void deleteBookWithStatusToDelete();
	void deleteBookWithStatusInProgress();
	void changeBookWithStatusToDeleteToOk();
	void deleteBookWithStatusOk();
	long getCountData();


}
