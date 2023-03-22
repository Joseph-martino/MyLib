package com.mylib.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylib.core.entities.Book;
import com.mylib.core.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book getById(long id) {
		return this.bookRepository.getById(id);
	}
	
	public List<Book> getBooksList(){
		return this.bookRepository.getBooksList();
	}
	
	
	public Book createBook(Book book) {
		this.bookRepository.createBook(book);
		return book;
	}
	
	

}
