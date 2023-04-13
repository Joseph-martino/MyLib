import { Component } from '@angular/core';
import { BookService } from '../services/book.service';
import { OnInit } from '@angular/core';
import { Observable, map, tap, combineLatest, switchMap, debounceTime } from 'rxjs';
import { Book } from '../models/book.model';
import { ActivatedRoute } from '@angular/router';


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
  pageNumber: number = 1;
  pageSize: number = 9;
  totalNumberOfData!: Observable<number>;
  totalPages$!: Observable<number>

  page$: Observable<number> = this.activatedRoute.queryParams.pipe(map(({page}) => page ? +page : 1));
  size$: Observable<number> = this.activatedRoute.queryParams.pipe(map(({size}) => size ? +size : 9));

  constructor(private bookService: BookService, private activatedRoute: ActivatedRoute){
  
  }

  ngOnInit(): void {
    this.totalNumberOfData = this.bookService.getCountData();
    
    this.totalPages$ = this.totalNumberOfData.pipe(
      tap(value => console.log(value)),
      map(numberOfPage => numberOfPage /this.pageSize),
      tap(myNumber => console.log(myNumber))
    );
    //boolean =true et afficher le gif (on peut utiliser Angular material ???)
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
    // dans une promise then=> boolean a false et on n'affiche plus le gif de chargement, avec les observables utiliser un pipe ????

    this.books$ = combineLatest({
      page: this.page$,
      size: this.size$,
  }).pipe(
      debounceTime(200),
      switchMap(({ page, size }) => {
          return this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, page, size);
      })
  );

    this.totalPages$ = combineLatest({
      total: this.bookService.getCountData(),
      size: this.size$,
  }).pipe(
      switchMap(({ total, size }) => {
          return Math.ceil(total / size)
      })
  );
  }
  

  //pagination bar angular
//https://javascript.plainenglish.io/create-a-simple-pagination-component-in-angular-17b909ea03e1

  onPrevious(): void {
    this.pageNumber = this.pageNumber - 1;
    console.log(this.pageNumber);
    if(this.pageNumber <= 1){
      this.pageNumber = 1
    }
    //relancer la methode qui récupère tous les livres
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
  }

  onNext(): void {
    this.pageNumber = this.pageNumber + 1;
    console.log(this.pageNumber);
    // Prévoir la dernière page (en comptant les entrées de la base de données)
    //relancer la methode qui récupère tous les livres
    this.books$ = this.bookService.getBooksList(this.authorName, this.illustratorName, this.editorName, this.collectionName, this.pageNumber, this.pageSize);
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
