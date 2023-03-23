package com.mylib.core.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylib.core.dto.AuthorDto;
import com.mylib.core.dto.BookDto;
import com.mylib.core.dto.CollectionDto;
import com.mylib.core.dto.EditorDto;
import com.mylib.core.dto.IllustratorDto;
import com.mylib.core.entities.Author;
import com.mylib.core.entities.Book;
import com.mylib.core.entities.Collection;
import com.mylib.core.entities.Editor;
import com.mylib.core.entities.Illustrator;
import com.mylib.core.repositories.BookRepository;



@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public BookDto getById(long id) {
		BookDto bookDto = new BookDto();
		AuthorDto authorDto = new AuthorDto();
		IllustratorDto illustratorDto = new IllustratorDto();
		EditorDto editorDto = new EditorDto();
		CollectionDto collectionDto = new CollectionDto();
		
		
		Book book = this.bookRepository.getById(id);
		
		authorDto.setId(book.getAuthor().getId());
		authorDto.setFullName(book.getAuthor().getFullName());
		
		illustratorDto.setId(book.getIllustrator().getId());
		illustratorDto.setFullName(book.getIllustrator().getFullName());
		
		editorDto.setId(book.getEditor().getId());
		editorDto.setName(book.getEditor().getName());
		
		collectionDto.setId(book.getCollection().getId());
		collectionDto.setName(book.getCollection().getName());
		
		bookDto.setId(book.getId());
		bookDto.setTitle(book.getTitle());
		bookDto.setAuthor(authorDto);
		bookDto.setIllustrator(illustratorDto);
		bookDto.setEditor(editorDto);
		bookDto.setCollection(collectionDto);
		
		return bookDto;
	}
	
	public List<BookDto> getBooksList(){
		List<Book> books = this.bookRepository.getBooksList();
		List<BookDto> booksDto = new ArrayList<>();
		for(Book book: books) {
			BookDto bookDto = new BookDto();
			AuthorDto authorDto = new AuthorDto();
			IllustratorDto illustratorDto = new IllustratorDto();
			EditorDto editorDto = new EditorDto();
			CollectionDto collectionDto = new CollectionDto();
			
			authorDto.setId(book.getAuthor().getId());
			authorDto.setFullName(book.getAuthor().getFullName());
			illustratorDto.setId(book.getIllustrator().getId());
			illustratorDto.setFullName(book.getIllustrator().getFullName());
			editorDto.setId(book.getEditor().getId());
			editorDto.setName(book.getEditor().getName());
			collectionDto.setId(book.getCollection().getId());
			collectionDto.setName(book.getCollection().getName());
			bookDto.setId(book.getId());
			bookDto.setTitle(book.getTitle());
			bookDto.setAuthor(authorDto);
			bookDto.setIllustrator(illustratorDto);
			bookDto.setEditor(editorDto);
			bookDto.setCollection(collectionDto);
			
			booksDto.add(bookDto);
		}
		
		return booksDto;
	}
	
	
	public BookDto createBook(BookDto bookDto) {
		Book book = new Book();
		Author author = new Author();
		Illustrator illustrator = new Illustrator();
		Editor editor = new Editor();
		Collection collection = new Collection();
		
		//Vérifier si l'auteur, l'illustrateur.... existent et si oui récupère l'id
		//book.setAuthor (Author que j'ai récupéré depuis la base de données)
		//S'il non alors nouvelle instance de author et persist(author)
		author.setFullName(bookDto.getAuthor().getFullName());
		illustrator.setFullName(bookDto.getIllustrator().getFullName());
		editor.setName(bookDto.getEditor().getName());
		collection.setName(bookDto.getCollection().getName());
		
		book.setTitle(bookDto.getTitle());
		book.setAuthor(author);
		book.setIllustrator(illustrator);
		book.setEditor(editor);
		book.setCollection(collection);
		
		this.bookRepository.createBook(book);
		return bookDto;
	}
	
	

}
