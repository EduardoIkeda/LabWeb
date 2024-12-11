import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { PostoSaude } from '../../../shared/model/posto-saude';
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
  @Input() postosSaude: PostoSaude[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);

  readonly displayedColumns: string[] = ['nome', 'horarioAbertura', 'horarioFechamento', 'actions'];

  constructor() {}

  ngOnInit(): void {}

  onAdd() {
    this.add.emit(true);
  }

  onEdit(postoSaude: PostoSaude) {
    this.edit.emit(postoSaude);
  }

  onRemove(postoSaude: PostoSaude) {
    this.remove.emit(postoSaude);
  }
}
