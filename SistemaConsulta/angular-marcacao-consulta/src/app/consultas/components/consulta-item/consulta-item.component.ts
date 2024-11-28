import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Consulta } from '../../model/consulta';

@Component({
  selector: 'app-consulta-item',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule, MatIconModule],
  templateUrl: './consulta-item.component.html',
  styleUrl: './consulta-item.component.scss',
})
export class ConsultaItemComponent {
  @Input() consulta!: Consulta;
  @Output() reschedule = new EventEmitter<Consulta>();
  @Output() cancel = new EventEmitter<Consulta>();

  onReschedule(event: Event) {
    event.stopPropagation();
    this.reschedule.emit(this.consulta);
  }

  onCancel(event: Event) {
    event.stopPropagation();
    this.cancel.emit(this.consulta);
  }
}
