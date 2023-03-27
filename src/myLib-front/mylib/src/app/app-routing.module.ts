import { NgModule } from "@angular/core";
import { RouterModule,Routes } from "@angular/router";
import { BookListComponent } from "./book-list/book-list.component";
import { BookComponent } from "./book/book.component";
import { SingleBookComponent } from "./single-book/single-book.component";

const routes: Routes = [
    {path: ':id', component: SingleBookComponent},
    {path: '', component: BookListComponent}
    
]

@NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],

    exports: [
        RouterModule
    ]
})
export class AppRoutingModule {

}