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
  constructor(private bookService: BookService){

  }

  ngOnInit(): void {
    //boolean =true et afficher le gif (on peut utiliser Angular material ???)
    this.books$ = this.bookService.getAllBooks();
    // dans une promise then=> boolean a false et on n'affiche plus le gif de chargement, avec les observables utiliser un pipe ????
  }

  getBooksByAuthorName(authorName: string){
    this.books$ = this.bookService.getBookByAuthorName(authorName);
  }

}
