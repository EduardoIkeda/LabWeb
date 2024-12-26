import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { firstValueFrom, Observable, of } from 'rxjs';

import { AdvancePieChartComponent } from '../../components/advance-pie-chart/advance-pie-chart.component';
import { LineChartComponent } from '../../components/line-chart/line-chart.component';
import { RankingChartComponent } from '../../components/ranking-chart/ranking-chart.component';
import { AnosComConsultas, ConsultasReport, EspecialidadeReport, Report } from '../../model/consultas-report';
import { ConsultasReportService } from '../../service/consultas-report.service';

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
  years: number[] = [];
  months: number[] = [];
  selectedYear: number = 2024;
  selectedMonth: number = 12;

  anosComConsultas: AnosComConsultas[] = [];

  lineChartTitle = 'Consultas marcadas e canceladas por mês';
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

  async ngOnInit(): Promise<void> {
    this.anosComConsultas = await firstValueFrom(this.consultasService.getAnosComConsultas());

    this.years = this.getYears();
    //this.selectedYear = this.getLastStoredYear();
    //this.selectedMonth = this.getLastStoredMonth(this.selectedYear);

    this.loadConsultasData();
  }

  loadConsultasData(): void {
    this.pieChartData$ = this.transformDataForPieChart();
    this.lineChartData$ = this.transformDataForLineChart();
    this.rankingData$ = this.consultasService.getEspecialidadesMaisConsultadas(this.selectedYear);

    this.months = this.getAvailableMonths(this.selectedYear);
  }

  transformDataForPieChart(): Observable<Report[]> {
    const yearData = this.anosComConsultas.find((entry) => entry.year === this.selectedYear);
    if (!yearData) {
      console.error(`Ano ${this.selectedYear} não encontrado.`);
      return of([]);
    }

    const monthData = yearData.monthlyStats.find((month) => month.month === this.selectedMonth);
    if (!monthData) {
      console.error(`Mês ${this.selectedMonth} não encontrado para o ano ${this.selectedYear}.`);
      return of([]);
    }

    const transformedData: Report[] = [
      { name: "Comparecido", value: monthData.attendedCount },
      { name: "Não comparecido", value: monthData.missedCount },
      { name: "Cancelado", value: monthData.cancelledCount }
    ];

    return of(transformedData);
  }

  transformDataForLineChart(): Observable<ConsultasReport[]> {
    const yearData = this.anosComConsultas.find((entry) => entry.year === this.selectedYear);
    if (!yearData) {
      console.error(`Ano ${this.selectedYear} não encontrado.`);
      return of([]);
    }

    const consultasMarcadas = yearData.monthlyStats.map((month) => ({
      name: month.month.toString(),
      value: month.scheduledCount
    }));

    const consultasCanceladas = yearData.monthlyStats.map((month) => ({
      name: month.month.toString(),
      value: month.cancelledCount
    }));

    const transformedData = [
      {
        name: "Consultas marcadas",
        series: consultasMarcadas
      },
      {
        name: "Consultas canceladas",
        series: consultasCanceladas
      }
    ];

    return of(transformedData);
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
