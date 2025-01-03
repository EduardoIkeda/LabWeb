import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { Especialidade } from '../../../../shared/model/especialidade';

@Component({
  selector: 'app-especialidade-item',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule, MatIconModule],
  templateUrl: './especialidade-item.component.html',
  styleUrl: './especialidade-item.component.scss'
})
export class EspecialidadeItemComponent {
  @Input() especialidade!: Especialidade;
  @Output() select = new EventEmitter<Especialidade>();

  onSelect(event: Event){
    event.stopPropagation();
    this.select.emit(this.especialidade);
  }
}
