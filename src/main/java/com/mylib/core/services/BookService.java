package com.mylib.core.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.mylib.core.repositories.AuthorRepository;
import com.mylib.core.repositories.CollectionRepository;
import com.mylib.core.repositories.EditorRepository;
import com.mylib.core.repositories.IBookRepository;
import com.mylib.core.repositories.IllustratorRepository;



@Service
public class BookService {
	
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private IllustratorRepository illustratorRepository;
	@Autowired
	private EditorRepository editorRepository;
	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private IBookRepository bookRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
	
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
			BookDto bookDto = createBookDto(book);
			booksDto.add(bookDto);
		}
		return booksDto;
	}
	
	public List<Book> getAll(){
		List<Book> books = this.bookRepository.getAll();
		return books;	
	}
	
	public List<Book> getBooksByAuthorName(String authorName){
		return this.bookRepository.getBooksByAuthor(authorName);
	}
	
	public BookDto createBook(BookDto bookDto) {
		//Vérifier si l'auteur, l'illustrateur.... existent et si oui récupère l'id
		//book.setAuthor (Author que j'ai récupéré depuis la base de données)
		//S'il non alors nouvelle instance de author et persist(author)
		Author author = new Author();
		author.setFullName(bookDto.getAuthor().getFullName());
		
		Illustrator illustrator = new Illustrator();
		illustrator.setFullName(bookDto.getIllustrator().getFullName());
		
		Editor editor = new Editor();
		editor.setName(bookDto.getEditor().getName());
		
		Collection collection = new Collection();
		collection.setName(bookDto.getCollection().getName());
		
		Book book = new Book();
		book.setTitle(bookDto.getTitle());
		book.setAuthor(author);
		book.setIllustrator(illustrator);
		book.setEditor(editor);
		book.setCollection(collection);
		
		this.bookRepository.createBook(book);
		return bookDto;
	}
	
	public void createBook(Book book) {
		this.bookRepository.createBook(book);
	}
	
	public BookDto updateBook(BookDto bookDto) {
		Author author = new Author();
		author.setFullName(bookDto.getAuthor().getFullName());
		
		Illustrator illustrator = new Illustrator();
		illustrator.setFullName(bookDto.getIllustrator().getFullName());
		
		Editor editor = new Editor();
		editor.setName(bookDto.getEditor().getName());
		
		Collection collection = new Collection();
		collection.setName(bookDto.getCollection().getName());
		
		Book book = new Book();
		book.setId(bookDto.getId());
		book.setTitle(bookDto.getTitle());
		book.setAuthor(author);
		book.setIllustrator(illustrator);
		book.setEditor(editor);
		book.setCollection(collection);
		this.bookRepository.updateBook(book);
		return bookDto;
	}
	
	public void deleteBook(long id) {
		this.bookRepository.deleteBook(id);
	}
	
	public void deleteAllFromDatabase() {
		this.bookRepository.deleteAllFromDatabase();
	}
	
	@Transactional
	public void lineParser(String line) {
		Book book = new Book();
        final String[] bookInformationsRow = line.split(";", -1);
        
        if(!"".equals(bookInformationsRow[0])) {
    		book.setTitle(bookInformationsRow[0]);
    	} else {
    		book.setTitle("Nom inconnu");
    	}
    		
    	
    	String authorFullName = !"".equals(bookInformationsRow[1]) ? bookInformationsRow[1] : "Nom inconnu";

		Author author = this.authorRepository.getByFullName(authorFullName);
		 if(author == null) {
			author = new Author();
			author.setFullName(authorFullName);
			this.authorRepository.save(author);
		}
		book.setAuthor(author);	 
    		
    
		String illustratorName = !"".equals(bookInformationsRow[2]) ? bookInformationsRow[2] : "Nom inconnu";
		Illustrator illustrator = this.illustratorRepository.getByFullName(illustratorName);
		
		if(illustrator == null) {
			illustrator = new Illustrator();
			illustrator.setFullName(illustratorName);
			this.illustratorRepository.save(illustrator);
		} 
		book.setIllustrator(illustrator);	
		
		String editorName = !"".equals(bookInformationsRow[3]) ? bookInformationsRow[3] : "Nom inconnu";
		Editor editor = this.editorRepository.getByName(editorName);
		if(editor == null) {
			editor = new Editor();
			editor.setName(editorName);
			this.editorRepository.save(editor);
		} 
		book.setEditor(editor);
	
		String collectionName = !"".equals(bookInformationsRow[4]) ? bookInformationsRow[4] : "Nom inconnu";
		Collection collection = this.collectionRepository.getByName(collectionName);
		if(collection == null) {
			collection = new Collection();
			collection.setName(collectionName);
			this.collectionRepository.save(collection);
		} 
		book.setCollection(collection);	
		
		this.bookRepository.createBook(book);
		
		LOGGER.info("Livre crée avec succès. Titre: {} auteur: {}", book.getTitle(), book.getAuthor().getFullName());
        LOGGER.info("Illustrator: {} , Editeur: {}", book.getIllustrator().getFullName(), book.getEditor().getName());
        LOGGER.info("Collection: {}", book.getCollection().getName());
		
		this.bookRepository.createBook(book);
		return bookDto;
	}
	
	

}
