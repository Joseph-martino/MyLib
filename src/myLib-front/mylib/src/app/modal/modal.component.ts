import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { BookService } from '../services/book.service';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit{

  @Output() onDeleted = new EventEmitter<boolean>();
  isDeleted!: boolean;
  message!: string;
  snapId!: number;
  success!: boolean;

  constructor(private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router,){

  }

  ngOnInit(): void {
    this.isDeleted = false;
    this.snapId = +this.route.snapshot.params['id'];
    //this.book$ = this.bookService.getBookById(this.snapId);  
  }

  closeModal(){
    this.isDeleted = false;
    this.onDeleted.emit(this.isDeleted);
  }

  onDeleteBook():void{
    this.isDeleted = true;
    this.bookService.deleteBook(this.snapId).pipe(
      tap(()=> this.router.navigateByUrl('/books'))
    ).subscribe();
    this.message = "Le livre a été supprimé avec succès";
    this.success = true;
    setTimeout(() => this.success = false, 3000);
  }

}
