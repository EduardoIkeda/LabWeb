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
    { nome: 'Nome 1', quantidade: 100 },
    { nome: 'Nome 2', quantidade: 200 },
    { nome: 'Nome 3', quantidade: 300 },
    { nome: 'Nome 4', quantidade: 400 },
    { nome: 'Nome 5', quantidade: 500 },
    { nome: 'Nome 6', quantidade: 600 },
    { nome: 'Nome 7', quantidade: 700 },
    { nome: 'Nome 8', quantidade: 800 },
    { nome: 'Nome 9', quantidade: 900 },
    { nome: 'Nome 10', quantidade: 1000 },
  ];

  column1: EspecialidadeReport[] = [];
  column2: EspecialidadeReport[] = [];

  ngOnInit(): void {
    this.sortAndSplitRankingItems();
  }

  private sortAndSplitRankingItems(): void {
    // Ordenar a lista pela pontuação (maior para menor)
    this.rankingItems.sort((a, b) => b.quantidade - a.quantidade);

    // Pegar até 10 elementos
    const topItems = this.rankingItems.slice(0, 10);

    // Dividir a lista em duas colunas, preenchendo a coluna1 primeiro
    this.column1 = topItems.slice(0, 5);
    this.column2 = topItems.slice(5, 10);

    // Garantir que cada coluna tenha pelo menos 5 elementos
    while (this.column1.length < 5) {
      this.column1.push({ nome: '', quantidade: 0 });
    }
    while (this.column2.length < 5) {
      this.column2.push({ nome: '', quantidade: 0 });
    }
  }

  isLastItem(
    column: EspecialidadeReport[],
    item: EspecialidadeReport
  ): boolean {
    return column.indexOf(item) === column.length - 1;
  }
}
