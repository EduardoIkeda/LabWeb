import { Posto } from './../../../../shared/model/posto';
import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-posto-item',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule, MatIconModule],
  templateUrl: './posto-item.component.html',
  styleUrl: './posto-item.component.scss'
})
export class PostoItemComponent {
  @Input() posto!: Posto;
  @Output() select = new EventEmitter<Posto>();

  onSelect(event: Event){
    event.stopPropagation();
    this.select.emit(this.posto);
  }
}
