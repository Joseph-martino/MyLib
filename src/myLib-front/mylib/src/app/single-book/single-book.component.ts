import { Component } from '@angular/core';
import { Book } from '../models/book.model';
import { ActivatedRoute } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { OnInit } from '@angular/core';
import { BookService } from '../services/book.service';

@Component({
  selector: 'app-single-book',
  templateUrl: './single-book.component.html',
  styleUrls: ['./single-book.component.scss']
})
export class SingleBookComponent implements OnInit{

  book$!: Observable<Book>;

  constructor(private bookService: BookService,
    private route: ActivatedRoute){
  }

  ngOnInit(): void {
    const snapId = +this.route.snapshot.params['id'];
    this.book$ = this.bookService.getBookById(snapId);
  }



}
