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

  book$!: Observable<Book[]>;
  constructor(private bookService: BookService){

  }

  ngOnInit(): void {

    this.book$ = this.bookService.getAllBooks();
    
  }

}
