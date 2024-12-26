import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

import { AdvancePieChartComponent } from '../../components/advance-pie-chart/advance-pie-chart.component';
import { LineChartComponent } from '../../components/line-chart/line-chart.component';
import { RankingChartComponent } from '../../components/ranking-chart/ranking-chart.component';
import { AnosComConsultas, EspecialidadeReport } from '../../model/consultas-report';
import { ConsultasReportService } from '../../service/consultas-report.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    MatCardModule,
    CommonModule,
    LineChartComponent,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    AdvancePieChartComponent,
    RankingChartComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  selectedYear: number = 2025;
  selectedMonth: number = 2;
  months: number[] = [];
  years: number[] = [];
  anosComConsultas: AnosComConsultas[] = [];

  lineChartTitle = 'Consultas canceladas por mês';
  lineChartxAxisLabel = 'Mês';
  lineChartyAxisLabel = 'Número de consultas';
  lineChartData$: Observable<any[]> = new Observable<any[]>();
  lineChartView: [number, number] = [700, 400];

  pieChartTitle = 'Consultas no mês';
  pieChartData$: Observable<any[]> = new Observable<any[]>();

  rankingTitle: string = 'Especialidades mais consultadas';
  rankingData$: Observable<EspecialidadeReport[]> = new Observable<EspecialidadeReport[]>();

  constructor(
    private readonly consultasService: ConsultasReportService
  ) { }

  ngOnInit(): void {
    this.consultasService.getAnosComConsultas().subscribe((data) => {
      this.anosComConsultas = data;
    });

    this.years = this.getYears();
    this.selectedYear = this.getLastStoredYear();
    this.selectedMonth = this.getLastStoredMonth(this.selectedYear);

    this.loadConsultasData();
  }

  loadConsultasData(): void {
    this.pieChartData$ = this.consultasService.getConsultasMarcadasPorMes(this.selectedYear, this.selectedMonth);
    this.lineChartData$ = this.consultasService.getConsultasCanceladasNoAno(this.selectedYear);
    this.rankingData$ = this.consultasService.getEspecialidadesMaisConsultadas(this.selectedYear);

    this.months = this.getAvailableMonths(this.selectedYear);
  }

  onYearChange(year: number): void {
    this.selectedYear = year;
    this.loadConsultasData();
  }

  onMonthChange(month: number): void {
    this.selectedMonth = month;
    this.loadConsultasData();
  }

  getYears(): number[] {
    return this.anosComConsultas.map((ano) => ano.year);
  }

  getLastStoredYear(): number {
    return this.anosComConsultas.length
      ? Math.max(...this.anosComConsultas.map((ano) => ano.year))
      : new Date().getFullYear();
  }

  getAvailableMonths(year: number): number[] {
    const ano = this.anosComConsultas.find((ano) => ano.year === year);

    if (ano) {
      return ano.monthlyStats.map((month) => month.month);
    }

    return [];
  }

  getLastStoredMonth(year: number): number {
    const ano = this.anosComConsultas.find((ano) => ano.year === year);

    if (ano) {
      return Math.max(...ano.monthlyStats.map((month) => month.month));
    }

    return new Date().getMonth() + 1;
  }
}
