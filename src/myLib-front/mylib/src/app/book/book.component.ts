import { Component, Input } from '@angular/core';
import { Book } from '../models/book.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent {
  @Input() book!: Book;

  constructor(private router: Router){

  }

  onViewBook(){

    this.router.navigateByUrl(`${this.book.id}`)

  }
}
