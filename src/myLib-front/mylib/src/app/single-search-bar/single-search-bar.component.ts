import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-single-search-bar',
  templateUrl: './single-search-bar.component.html',
  styleUrls: ['./single-search-bar.component.scss']
})
export class SingleSearchBarComponent{
  @Output() authorSearched = new EventEmitter<string>();

  onEnter(value: string) { 
    this.authorSearched.emit(value);
  }
}
