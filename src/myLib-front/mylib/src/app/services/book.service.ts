import { HttpClient, HttpParams } from "@angular/common/http";
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

    getBooksList(authorName: string, illustratorName: string, editorName: string, collectionName: string, pageNumber: number, pageSize: number): Observable<Book[]>{

    // https://stackoverflow.com/questions/45470575/angular-4-httpclient-query-parameters
    // On initialise l'objet params
    let params = new HttpParams();

    // On assigne les paramètres si ceux-ci existent
    if (authorName) {
        params = params.append('authorName', authorName);
    }

    if (illustratorName) {
        params = params.append('illustratorName', illustratorName);
    }

    if (editorName) {
        params = params.append('editorName', editorName);
    }

    if (collectionName) {
        params = params.append('collectionName', collectionName);
    }

    params = params.append('pageNumber', pageNumber);
    params = params.append('pageSize', pageSize);
    //On ajoute l'objet params à la requêtes http get
    return this.http.get<Book[]>(`${this.url}/mylib/books/authorBookList`, { params: params });
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