package com.mylib.core.repositories;

import com.mylib.core.entities.Book;

public interface IBookRepository {
	
	void createBook(Book book);
	Book getByTitle(String title);
	Book getById(long id);

}
