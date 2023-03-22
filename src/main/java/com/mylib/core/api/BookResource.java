package com.mylib.core.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylib.core.entities.Book;
import com.mylib.core.services.BookService;

@RestController
@RequestMapping("/book")
public class BookResource {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public List<Book> getBooksList(){
		return this.bookService.getBooksList();
	}
	
	@GetMapping("/{id}")
	public Book getById(@PathVariable("id") long id) {
		return this.bookService.getById(id);
	}
	
	@PostMapping("/create")
	public Book createBook(@RequestBody Book book) {
		return this.bookService.createBook(book);
		
	}
	

}
