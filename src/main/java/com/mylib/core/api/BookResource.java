package com.mylib.core.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylib.core.dto.BookDto;
import com.mylib.core.entities.Book;
import com.mylib.core.services.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:4200/") 
public class BookResource {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public List<BookDto> getBooksList(){
		return this.bookService.getBooksList();
	}
	
	@GetMapping("/{id}")
	public BookDto getById(@PathVariable("id") long id) {
		return this.bookService.getById(id);
	}
	
	@PostMapping("/create")
	public BookDto createBook(@RequestBody BookDto bookDto) {
		return this.bookService.createBook(bookDto);
		
	}
	

}
