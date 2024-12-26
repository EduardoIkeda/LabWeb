import { EspecialidadeReport } from './../../model/consultas-report';
import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-ranking-chart',
  standalone: true,
  imports: [MatCardModule, MatListModule, CommonModule],
  templateUrl: './ranking-chart.component.html',
  styleUrls: ['./ranking-chart.component.scss'],
})
export class RankingChartComponent implements OnInit {
  @Input() title: string = 'Especialidades mais consultadas';
  @Input() rankingItems: EspecialidadeReport[] = [
    { specialtyName: 'Nome 1', appointmentsCount: 100 },
    { specialtyName: 'Nome 2', appointmentsCount: 200 },
    { specialtyName: 'Nome 3', appointmentsCount: 300 },
    { specialtyName: 'Nome 4', appointmentsCount: 400 },
    { specialtyName: 'Nome 5', appointmentsCount: 500 },
    { specialtyName: 'Nome 6', appointmentsCount: 600 },
    { specialtyName: 'Nome 7', appointmentsCount: 700 },
    { specialtyName: 'Nome 8', appointmentsCount: 800 },
    { specialtyName: 'Nome 9', appointmentsCount: 900 },
    { specialtyName: 'Nome 10', appointmentsCount: 1000 }
  ];

  column1: EspecialidadeReport[] = [];
  column2: EspecialidadeReport[] = [];

  ngOnInit(): void {
    this.sortAndSplitRankingItems();
  }

  private sortAndSplitRankingItems(): void {
    // Ordenar a lista pela pontuação (maior para menor)
    this.rankingItems.sort((a, b) => b.appointmentsCount - a.appointmentsCount);

    console.log('sortAndSplitRankingItems');
    console.log(this.rankingItems);

    // Pegar até 10 elementos
    const topItems = this.rankingItems.slice(0, 10);

    // Dividir a lista em duas colunas, preenchendo a coluna1 primeiro
    this.column1 = topItems.slice(0, 5);
    this.column2 = topItems.slice(5, 10);

    // Garantir que cada coluna tenha pelo menos 5 elementos
    while (this.column1.length < 5) {
      this.column1.push({ specialtyName: '', appointmentsCount: 0 });
    }
    while (this.column2.length < 5) {
      this.column2.push({ specialtyName: '', appointmentsCount: 0 });
    }
  }

  isLastItem(
    column: EspecialidadeReport[],
    item: EspecialidadeReport
  ): boolean {
    return column.indexOf(item) === column.length - 1;
  }
}
