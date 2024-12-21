import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { HealthCenter } from '../../../shared/model/posto-saude';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-posto-saude-list',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    CommonModule,
    MatCardModule,
    MatIconModule,
  ],
  templateUrl: './posto-saude-list.component.html',
  styleUrl: './posto-saude-list.component.scss',
})
export class PostoSaudeListComponent {
  @Input() postosSaude: HealthCenter[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);
  @Output() editDoctor: EventEmitter<HealthCenter> = new EventEmitter<HealthCenter>();

  readonly displayedColumns: string[] = ['nome', 'horarioAbertura', 'horarioFechamento', 'actions'];

  constructor() {}

  ngOnInit(): void {}

  onAdd() {
    this.add.emit(true);
  }

  onEdit(postoSaude: HealthCenter) {
    this.edit.emit(postoSaude);
  }

  onRemove(postoSaude: HealthCenter) {
    this.remove.emit(postoSaude);
  }

  onEditDoctor(postoSaude: HealthCenter) {
    this.editDoctor.emit(postoSaude);
  }
}
