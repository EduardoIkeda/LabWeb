import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-child-component',
  standalone: true,
  imports: [MatButtonModule],
  templateUrl: './child-component.component.html',
  styleUrl: './child-component.component.scss',
})
export class ChildComponentComponent {
  @Input() item: string = '';
  @Output() itemClicked = new EventEmitter<string>();

  onClick(item: string) {
    this.itemClicked.emit(this.item);
  }
}
