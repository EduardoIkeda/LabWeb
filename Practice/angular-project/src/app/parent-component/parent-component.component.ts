import { Component } from '@angular/core';
import { ChildComponentComponent } from "../child-component/child-component.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-parent-component',
  standalone: true,
  imports: [ChildComponentComponent, CommonModule],
  templateUrl: './parent-component.component.html',
  styleUrl: './parent-component.component.scss'
})
export class ParentComponentComponent {
  selectedItem!: string;
  items = ['item1', 'item2', 'item3', 'item4'];

  onClick(name: string)
  {
    this.selectedItem = name;
  }
}
