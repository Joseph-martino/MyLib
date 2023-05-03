import { Component } from '@angular/core';
import { BookService } from '../services/book.service';
import { OnInit } from '@angular/core';
import {
  Observable,
  map,
  tap,
  delay,
  Subject,
  combineLatest,
  distinctUntilChanged,
  take,
  switchMap,
  BehaviorSubject,
} from 'rxjs';
import { Book } from '../models/book.model';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss'],
})
export class BookListComponent implements OnInit {
  books$!: Observable<Book[]>;
  isLoading: boolean;
  pageSize: number = 12;
  totalPagesNumber$!: Observable<number>;
  currentPageNumber$!: BehaviorSubject<number>;
  authorName$!: BehaviorSubject<string>;
  illustratorName$!: BehaviorSubject<string>;
  editorName$!: BehaviorSubject<string>;
  collectionName$!: BehaviorSubject<string>;

  constructor(private bookService: BookService) {
    this.isLoading = false;
  }

  ngOnInit(): void {
    this.currentPageNumber$ = new BehaviorSubject<number>(1);
    this.authorName$ = new BehaviorSubject<string>('');
    this.illustratorName$ = new BehaviorSubject<string>('');
    this.editorName$ = new BehaviorSubject<string>('');
    this.collectionName$ = new BehaviorSubject<string>('');
    
    const totalNumberOfData = this.bookService.getCountData();

    this.totalPagesNumber$ = totalNumberOfData.pipe(
      map((numberOfPage) => Math.ceil(numberOfPage / this.pageSize))
    );

    this.books$ = combineLatest([
      this.authorName$,
      this.illustratorName$,
      this.editorName$,
      this.collectionName$,
      this.currentPageNumber$,
    ]).pipe(
      tap(() => {
        this.isLoading = true;
      }),
      switchMap(([author, illustrator, editor, collection, currentPage]) => {
        return this.bookService.getBooksList(
          author,
          illustrator,
          editor,
          collection,
          currentPage -1,
          this.pageSize
        );
      }),
      tap(() => {
        this.isLoading = false;
      })
    );
    // dans une promise then=> boolean a false et on n'affiche plus le gif de chargement, avec les observables utiliser un pipe ????
  }
  //subject: créer un subject chacun pour authorName, Illustratorname... et les passer dans le combineLatest, et appeler la méthode de service à la fin de combineLastest

  //pagination bar angular
  //https://javascript.plainenglish.io/create-a-simple-pagination-component-in-angular-17b909ea03e1

  getCurrentPage(pageNumber: number) {
    this.currentPageNumber$.next(pageNumber);
  }

  getAuthorName(authorName: string) {
    this.authorName$.next(authorName);
  }

  getIllustratorName(illustratorName: string) {
    this.illustratorName$.next(illustratorName);
  }

  getEditorName(editorName: string) {
    this.editorName$.next(editorName);
  }

  getCollectionName(collectionName: string) {
    this.collectionName$.next(collectionName);
  }
}
