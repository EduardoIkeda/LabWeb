import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { HealthCenter } from '../../../../shared/model/health-center';

@Component({
  selector: 'app-posto-item',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule, MatIconModule],
  templateUrl: './posto-item.component.html',
  styleUrl: './posto-item.component.scss'
})
export class PostoItemComponent {
  @Input() posto!: HealthCenter;
  @Output() select = new EventEmitter<HealthCenter>();

  onSelect(event: Event){
    event.stopPropagation();
    this.select.emit(this.posto);
  }
}
