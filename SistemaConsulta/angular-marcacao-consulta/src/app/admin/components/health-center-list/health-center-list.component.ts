import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';

import { HealthCenter } from '../../../shared/model/health-center';

@Component({
  selector: 'app-health-center-list',
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
  templateUrl: './health-center-list.component.html',
  styleUrl: './health-center-list.component.scss',
})
export class HealthCenterListComponent {
  @Input() healthCenterData: HealthCenter[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);
  @Output() editDoctor: EventEmitter<HealthCenter> =
    new EventEmitter<HealthCenter>();

  readonly displayedColumns: string[] = [
    'nome',
    'horarioAbertura',
    'horarioFechamento',
    'actions',
  ];

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
