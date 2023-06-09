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
	List<Book> getBooksByAuthor(String authorName);

}
