import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination-bar',
  templateUrl: './pagination-bar.component.html',
  styleUrls: ['./pagination-bar.component.scss']
})
export class PaginationBarComponent {
  @Input() totalNumberPage!: number | null;
  @Input() currentPageNumber!: number | null;
  @Output() onSelectedPage = new EventEmitter<number>();
  
  goToPage(pageNumber: number){
    this.onSelectedPage.emit(pageNumber - 1);
  }

  createIntermediatePageNumbers(){
    const windowSize: number = 2;
    const numbers: number[] = [];
    if(this.currentPageNumber !== null && this.totalNumberPage !== null){
      const leftNumbers = Math.max(1, this.currentPageNumber - windowSize);
      const rightNumbers = Math.min(this.totalNumberPage, this.currentPageNumber + windowSize);
      
      for(let i = leftNumbers; i <= rightNumbers; i++){
          numbers.push(i);
      }
    }
    return numbers;
  }
}
