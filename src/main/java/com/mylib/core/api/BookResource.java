package com.mylib.core.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylib.core.dto.BookDto;
import com.mylib.core.entities.Book;
import com.mylib.core.services.BookService;

@RestController
@RequestMapping("/books")
public class BookResource {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public List<BookDto> getBooksList(){
		return this.bookService.getBooksList();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getById(@PathVariable("id") long id) {
		BookDto bookDto = this.bookService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(bookDto);//Pour changer le statut code de la requête, ici 200 pour la lecture
	}
	
	@GetMapping("/authorBookList/{authorName}")
	public List<Book> getBookListByAuthorName(@PathVariable("authorName") String authorName){
		return this.bookService.getBooksByAuthorName(authorName);
	}
	
	@PostMapping("/create")
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
		BookDto BookDtoToReturn = this.bookService.createBook(bookDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(BookDtoToReturn); //Pour changer le statut code de la requête, ici 201 pour la création
	}
	
	@PutMapping("/update")
	public BookDto updateBook(@RequestBody BookDto bookDto) {
		return this.bookService.updateBook(bookDto);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteBook(@PathVariable("id") long id) {
		this.bookService.deleteBook(id);
	}
	

}
