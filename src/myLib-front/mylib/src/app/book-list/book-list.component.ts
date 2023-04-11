import { Component } from '@angular/core';
import { BookService } from '../services/book.service';
import { OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';


@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {

  books$!: Observable<Book[]>;
  authorName!: string;
  illustratorName!: string;
  editorName!: string;
  collectionName!: string;
  
  constructor(private bookService: BookService){
  
  }

  ngOnInit(): void {
    //boolean =true et afficher le gif (on peut utiliser Angular material ???)
    //this.books$ = this.bookService.getAllBooks();
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName);
    // dans une promise then=> boolean a false et on n'affiche plus le gif de chargement, avec les observables utiliser un pipe ????
  }

  getAuthorName(authorName: string){
    this.authorName = authorName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName);
  }

  getIllustratorName(illustratorName: string){
    this.illustratorName = illustratorName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName);
  }

  getEditorName(editorName: string){
    this.editorName = editorName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName);
  }

  getCollectionName(collectionName: string){
    this.collectionName = collectionName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName);
  }
}
