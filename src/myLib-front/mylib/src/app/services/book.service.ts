import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Book } from "../models/book.model";

@Injectable({
    providedIn: 'root'
})

export class BookService {

    constructor(private http: HttpClient){

    }

    getBookById(id: number): Observable<Book>{
        return this.http.get<Book>(`http://localhost:8080/mylib/book/${id}`)
    }

    getAllBooks(): Observable<Book[]>{
        return this.http.get<Book[]>('http://localhost:8080/mylib/book')
    }
    
}