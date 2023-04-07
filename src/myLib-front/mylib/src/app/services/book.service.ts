import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Author } from "../models/author.model";
import { Book } from "../models/book.model";
import { Collection } from "../models/collection.model";
import { Editor } from "../models/editor.model";
import { Illustrator } from "../models/illustrator.model";

@Injectable({
    providedIn: 'root'
})

export class BookService {

    book!: Book;
    author!: Author;
    illustrator!: Illustrator;
    editor!: Editor;
    collection!: Collection;
    private url: string;

    constructor(private http: HttpClient){
        this.url = "http://localhost:4200";
    }

    getBookById(id: number): Observable<Book>{
        return this.http.get<Book>(`${this.url}/mylib/books/${id}`);
    }

    getBookByAuthorName(authorName: string): Observable<Book[]>{
        return this.http.get<Book[]>(`${this.url}/mylib/books/authorBookList/${authorName}`);
    }

    getAllBooks(): Observable<Book[]>{
        return this.http.get<Book[]>(`${this.url}/mylib/books`);
    }

    addBook(formValue: {title: string, authorName: string, illustratorName: string, editorName: string, collectionName: string}): Observable<Book>{
        this.book = new Book();
        this.book.title = formValue.title;

        this.author = new Author();
        this.author.fullName = formValue.authorName;
        this.book.author = this.author;

        this.illustrator = new Illustrator();
        this.illustrator.fullName = formValue.illustratorName;
        this.book.illustrator = this.illustrator;

        this.editor = new Editor();
        this.editor.name = formValue.editorName;
        this.book.editor = this.editor;

        this.collection = new Collection();
        this.collection.name = formValue.collectionName;
        this.book.collection = this.collection;
        
        return this.http.post<Book>(`${this.url}/mylib/books/create`, this.book);
    }

    updateBook(id: number, formValue: {title: string, authorName: string, illustratorName: string, editorName: string, collectionName: string}): Observable<Book>{
        this.book = new Book();
        this.book.id = id;
        this.book.title = formValue.title;

        this.author = new Author();
        this.author.fullName = formValue.authorName;
        this.book.author = this.author;

        this.illustrator = new Illustrator();
        this.illustrator.fullName = formValue.illustratorName;
        this.book.illustrator = this.illustrator;

        this.editor = new Editor();
        this.editor.name = formValue.editorName;
        this.book.editor = this.editor;

        this.collection = new Collection();
        this.collection.name = formValue.collectionName;
        this.book.collection = this.collection;
        
        return this.http.put<Book>(`${this.url}/mylib/books/update`, this.book);
    }

    deleteBook(id: number):Observable<Book>{
        return this.http.delete<Book>(`${this.url}/mylib/books/delete/${id}`);
    }
}