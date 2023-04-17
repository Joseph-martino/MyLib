import { Component } from '@angular/core';
import { BookService } from '../services/book.service';
import { OnInit } from '@angular/core';
import { Observable, map, tap } from 'rxjs';
import { Book } from '../models/book.model';


@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {

  books$!: Observable<Book[]>;
  isLoading$!: Observable<boolean>;
  authorName!: string;
  illustratorName!: string;
  editorName!: string;
  collectionName!: string;
  pageNumber: number = 1;
  pageSize: number = 12;
  totalPagesNumber$!: Observable<number>;
  currentPageNumber: number;

  constructor(private bookService: BookService){
    this.currentPageNumber = 1;
  }

  ngOnInit(): void {
    const totalNumberOfData = this.bookService.getCountData();
    
    this.totalPagesNumber$ = totalNumberOfData.pipe(
      map(numberOfPage => Math.ceil(numberOfPage /this.pageSize)),
    );

    //boolean =true et afficher le gif (on peut utiliser Angular material ???)
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
    // dans une promise then=> boolean a false et on n'affiche plus le gif de chargement, avec les observables utiliser un pipe ????
    this.isLoading$ = this.books$.pipe(
      map(numberOdBooks => numberOdBooks.length <= 0),
    );
  }
  //subject: créer un subject chacun pour authorName, Illustratorname... et les passer dans le combineLatest, et appeler la méthode de service à la fin de combineLastest

  //pagination bar angular
//https://javascript.plainenglish.io/create-a-simple-pagination-component-in-angular-17b909ea03e1

  

  getCurrentPage(pageNumber: number){
    this.currentPageNumber = pageNumber;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.currentPageNumber, this.pageSize);
  }

  getAuthorName(authorName: string){
    this.authorName = authorName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
  }

  getIllustratorName(illustratorName: string){
    this.illustratorName = illustratorName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
  }

  getEditorName(editorName: string){
    this.editorName = editorName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
  }

  getCollectionName(collectionName: string){
    this.collectionName = collectionName;
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
  }
}
