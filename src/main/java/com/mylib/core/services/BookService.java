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
import com.mylib.core.enums.Status;
import com.mylib.core.repositories.AuthorRepository;
import com.mylib.core.repositories.CollectionRepository;
import com.mylib.core.repositories.EditorRepository;
import com.mylib.core.repositories.IBookRepository;
import com.mylib.core.repositories.IllustratorRepository;
import com.mylib.core.enums.Status;

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
		Book book = this.bookRepository.getById(id);
		BookDto bookDto = createBookDto(book);
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
	
	public List<BookDto> getAllBooksList(String authorName, String illustratorName, String editorName, String collectionName, int pageNumber, int pageSize){
		List<Book> books = this.bookRepository.getAllBooksList(authorName, illustratorName, editorName, collectionName, pageNumber, pageSize);
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
	
	public long getCountData() {
		return this.bookRepository.getCountData();
	}
	
	public BookDto createBook(BookDto bookDto) {
		//Vérifier si l'auteur, l'illustrateur.... existent et si oui récupère l'id
		//book.setAuthor (Author que j'ai récupéré depuis la base de données)
		//S'il non alors nouvelle instance de author et persist(author)
		Book book = new Book();
		Author authorFromDatabase = this.authorRepository.getByFullName(bookDto.getAuthor().getFullName());
		if(authorFromDatabase != null) {
			book.setAuthor(authorFromDatabase);
		} else {
			Author author = new Author();
			author.setFullName(bookDto.getAuthor().getFullName());
			author.setStatus(Status.OK.toString());
			this.authorRepository.createAuthor(author);
			book.setAuthor(author);
		}
		
		Illustrator illustratorFromDatabase = this.illustratorRepository.getByFullName(bookDto.getIllustrator().getFullName());
		if(illustratorFromDatabase != null) {
			book.setIllustrator(illustratorFromDatabase);
		} else {
			Illustrator illustrator = new Illustrator();
			illustrator.setFullName(bookDto.getIllustrator().getFullName());
			illustrator.setStatus(Status.OK.toString());
			this.illustratorRepository.createIllustrator(illustrator);
			book.setIllustrator(illustrator);
		}
		
		Editor editorFromDatabase = this.editorRepository.getByName(bookDto.getEditor().getName());
		if(editorFromDatabase != null) {
			book.setEditor(editorFromDatabase);
		} else {
			Editor editor = new Editor();
			editor.setName(bookDto.getEditor().getName());
			editor.setStatus(Status.OK.toString());
			this.editorRepository.createEditor(editor);
			book.setEditor(editor);
		}

		Collection collectionFromDatabase = this.collectionRepository.getByName(bookDto.getCollection().getName());
		if(collectionFromDatabase != null) {
			book.setCollection(collectionFromDatabase);
		} else {
			Collection collection = new Collection();
			collection.setName(bookDto.getCollection().getName());
			collection.setStatus(Status.OK.toString());
			this.collectionRepository.createCollection(collection);
			book.setCollection(collection);
		}
		
//		Illustrator illustrator = new Illustrator();
//		illustrator.setFullName(bookDto.getIllustrator().getFullName());
//		
//		Editor editor = new Editor();
//		editor.setName(bookDto.getEditor().getName());
//		
//		Collection collection = new Collection();
//		collection.setName(bookDto.getCollection().getName());
		
		book.setTitle(bookDto.getTitle());
		book.setStatus(Status.OK.toString());
		
		this.bookRepository.createBook(book);
		return bookDto;
	}
	
	public void createBook(Book book) {
		this.bookRepository.createBook(book);
	}
	
	public BookDto updateBook(BookDto bookDto) {
		Book book = new Book();
		Author authorFromDatabase = this.authorRepository.getByFullName(bookDto.getAuthor().getFullName());
		if(authorFromDatabase != null) {
			book.setAuthor(authorFromDatabase);
		} else {
			Author author = new Author();
			author.setFullName(bookDto.getAuthor().getFullName());
			author.setStatus(Status.OK.toString());
			this.authorRepository.createAuthor(author);
			book.setAuthor(author);
		}
		
		Illustrator illustratorFromDatabase = this.illustratorRepository.getByFullName(bookDto.getIllustrator().getFullName());
		if(illustratorFromDatabase != null) {
			book.setIllustrator(illustratorFromDatabase);
		} else {
			Illustrator illustrator = new Illustrator();
			illustrator.setFullName(bookDto.getIllustrator().getFullName());
			illustrator.setStatus(Status.OK.toString());
			this.illustratorRepository.createIllustrator(illustrator);
			book.setIllustrator(illustrator);
		}
		
		Editor editorFromDatabase = this.editorRepository.getByName(bookDto.getEditor().getName());
		if(editorFromDatabase != null) {
			book.setEditor(editorFromDatabase);
		} else {
			Editor editor = new Editor();
			editor.setName(bookDto.getEditor().getName());
			editor.setStatus(Status.OK.toString());
			this.editorRepository.createEditor(editor);
			book.setEditor(editor);
		}

		Collection collectionFromDatabase = this.collectionRepository.getByName(bookDto.getCollection().getName());
		if(collectionFromDatabase != null) {
			book.setCollection(collectionFromDatabase);
		} else {
			Collection collection = new Collection();
			collection.setName(bookDto.getCollection().getName());
			collection.setStatus(Status.OK.toString());
			this.collectionRepository.createCollection(collection);
			book.setCollection(collection);
		}
		
		book.setId(bookDto.getId());
		book.setTitle(bookDto.getTitle());
		book.setStatus(Status.OK.toString());
		this.bookRepository.updateBook(book);
		return bookDto;
	}
	
	public void deleteBook(long id) {
		this.bookRepository.deleteBook(id);
	}
	
	public void deleteAllFromDatabase() {
		this.bookRepository.deleteAllFromDatabase();
	}
	
	public BookDto createBookDto(Book book) {
		
		
		AuthorDto authorDto = new AuthorDto();
		authorDto.setId(book.getAuthor().getId());
		authorDto.setFullName(book.getAuthor().getFullName());
		
		IllustratorDto illustratorDto = new IllustratorDto();
		illustratorDto.setId(book.getIllustrator().getId());
		illustratorDto.setFullName(book.getIllustrator().getFullName());
		
		EditorDto editorDto = new EditorDto();
		editorDto.setId(book.getEditor().getId());
		editorDto.setName(book.getEditor().getName());
		
		CollectionDto collectionDto = new CollectionDto();
		collectionDto.setId(book.getCollection().getId());
		collectionDto.setName(book.getCollection().getName());
		
		BookDto bookDto = new BookDto();
		bookDto.setId(book.getId());
		bookDto.setTitle(book.getTitle());
		bookDto.setAuthor(authorDto);
		bookDto.setIllustrator(illustratorDto);
		bookDto.setEditor(editorDto);
		bookDto.setCollection(collectionDto);
		
		return bookDto;
	}
	
	public void changeBookStatutToOk() {
		this.bookRepository.changeBookStatusToOk();	
	}
	
	public void changeBookStatusToToDelete() {
		this.bookRepository.changeBookStatusToToDelete();	
	}
	
	public void deleteBookWithStatusToDelete() {
		this.bookRepository.deleteBookWithStatusToDelete();	
	}
	
	public void deleteBookWithStatusInProgress() {
		this.bookRepository.deleteBookWithStatusInProgress();	
	}
	
	public void changeBookWithStatusToDeleteToOk() {
		this.bookRepository.changeBookWithStatusToDeleteToOk();
	}
	
	public void deleteBookWithStatusOk() {
		this.bookRepository.deleteBookWithStatusOk();
	}
	
	@Transactional
	public void lineParser(String line) {
		Book book = new Book();
        final String[] bookInformationsRow = line.split(";", -1);
        
        if(!"".equals(bookInformationsRow[0])) {
    		book.setTitle(bookInformationsRow[0]);
    		book.setStatus(Status.IN_PROGRESS.toString());
    	} else {
    		book.setTitle("Nom inconnu");
    		book.setStatus(Status.IN_PROGRESS.toString());
    	}
    		
    	
    	String authorFullName = !"".equals(bookInformationsRow[1]) ? bookInformationsRow[1] : "Nom inconnu";

		Author author = this.authorRepository.getAuthorByNameAndStatus(authorFullName); // demander de chercher l'auteur font le statut est in progress
		 if(author == null) {
			author = new Author();
			author.setFullName(authorFullName);
			author.setStatus(Status.IN_PROGRESS.toString());
			this.authorRepository.save(author);
		}
		book.setAuthor(author);	 
    		
    
		String illustratorName = !"".equals(bookInformationsRow[2]) ? bookInformationsRow[2] : "Nom inconnu";
		Illustrator illustrator = this.illustratorRepository.getIllustratorByNameAndStatus(illustratorName);
		
		if(illustrator == null) {
			illustrator = new Illustrator();
			illustrator.setFullName(illustratorName);
			illustrator.setStatus(Status.IN_PROGRESS.toString());
			this.illustratorRepository.save(illustrator);
		} 
		book.setIllustrator(illustrator);	
		
		String editorName = !"".equals(bookInformationsRow[3]) ? bookInformationsRow[3] : "Nom inconnu";
		Editor editor = this.editorRepository.getEditorByNameAndStatus(editorName);
		if(editor == null) {
			editor = new Editor();
			editor.setName(editorName);
			editor.setStatus(Status.IN_PROGRESS.toString());
			this.editorRepository.save(editor);
		} 
		book.setEditor(editor);
	
		String collectionName = !"".equals(bookInformationsRow[4]) ? bookInformationsRow[4] : "Nom inconnu";
		Collection collection = this.collectionRepository.getCollectionByNameAndStatus(collectionName);
		if(collection == null) {
			collection = new Collection();
			collection.setName(collectionName);
			collection.setStatus(Status.IN_PROGRESS.toString());
			this.collectionRepository.save(collection);
		} 
		book.setCollection(collection);	
		
		this.bookRepository.createBook(book);
		
		LOGGER.info("Livre crée avec succès. Titre: {} auteur: {}", book.getTitle(), book.getAuthor().getFullName());
        LOGGER.info("Illustrator: {} , Editeur: {}", book.getIllustrator().getFullName(), book.getEditor().getName());
        LOGGER.info("Collection: {}", book.getCollection().getName());
	}
}
