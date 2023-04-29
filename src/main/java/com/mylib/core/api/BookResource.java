package com.mylib.core.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mylib.core.dto.BookDto;
import com.mylib.core.services.BookService;

@RestController
@RequestMapping("/books")
public class BookResource {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getById(@PathVariable("id") long id) {
		BookDto bookDto = this.bookService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(bookDto);//Pour changer le statut code de la requête, ici 200 pour la lecture
	}
	
	@GetMapping
	public List<BookDto> getBookList(@RequestParam(name="authorName", required = false) String authorName,
										@RequestParam(name="illustratorName", required = false) String illustratorName,
										@RequestParam( name="editorName", required = false) String editorName,
										@RequestParam( name="collectionName", required = false) String collectionName,
										@RequestParam( name="pageNumber") int pageNumber,
										@RequestParam( name="pageSize") int pageSize
										){
		return this.bookService.getAllBooksList(authorName, illustratorName, editorName, collectionName, pageNumber, pageSize);
	}
	
	@GetMapping("/total")
	public long getCountData() {
		return this.bookService.getCountData();
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
