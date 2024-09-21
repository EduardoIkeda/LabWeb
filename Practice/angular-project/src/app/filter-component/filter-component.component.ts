import { Component } from '@angular/core';
import { FilterListPipe } from './filter-list.pipe';
import { pipe } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-filter-component',
  standalone: true,
  imports: [FilterListPipe, CommonModule],
  templateUrl: './filter-component.component.html',
  styleUrl: './filter-component.component.scss',
  providers: [FilterListPipe]
})

export class FilterComponentComponent {
  users = [
    {name: 'John', age: 25},
    {name: 'Jane', age: 24},
    {name: 'Jim', age: 30},
    {name: 'Jill', age: 28},
    {name: 'Jack', age: 27}
  ]
  filterString: string = '';
  filteredList: any[] = [];

  constructor(private filterListPipe: FilterListPipe) {}

  ngOnInit() {
    this.filteredList = this.filterListPipe.transform(this.users, this.filterString, 'name');
  }

  onFilterChange(event: any) {
    this.filterString = event.target.value;
    this.filteredList = this.filterListPipe.transform(this.users, this.filterString, 'name');
  }

}
