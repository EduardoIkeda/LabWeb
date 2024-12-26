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
  selectedYear: number = 2022;
  selectedMonth: number = 2;
  months: number[] = [];
  years: number[] = [];
  anosComConsultas: AnosComConsultas[] = [];

  lineChartTitle = 'Consultas canceladas por mês';
  lineChartxAxisLabel = 'Mês';
  lineChartyAxisLabel = 'Número de consultas';
  // lineChartData: any[] = [];
  lineChartData$: Observable<any[]> = new Observable<any[]>();
  lineChartView: [number, number] = [700, 400];

  pieChartTitle = 'Consultas no mês';
  //pieChartData: any[] = [];
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

    this.loadConsultasData(this.selectedYear);
  }

  loadConsultasData(year: number): void {
    this.lineChartData$ = this.consultasService.getConsultasCanceladasNoAno(year);

    this.pieChartData$ = this.consultasService.getConsultasMarcadasPorMes(this.selectedMonth);

    this.months = this.getAvailableMonths(year);

    this.rankingData$ = this.consultasService.getEspecialidadesMaisConsultadas(year);
  }

  onYearChange(year: number): void {
    this.loadConsultasData(year);
  }

  onMonthChange(month: number): void {
    console.log('Month changed', month);
  }

  getYears(): number[] {
    return this.anosComConsultas.map((ano) => ano.year);
  }

  getLastStoredMonth(year: number): number {
    const ano = this.anosComConsultas.find((ano) => ano.year === year);
    return ano ? Math.max(...ano.month) : new Date().getMonth() + 1;
  }

  getAvailableMonths(year: number): number[] {
    return this.anosComConsultas.find((ano) => ano.year === year)?.month || [];
  }

  getLastStoredYear(): number {
    return this.anosComConsultas.length
      ? Math.max(...this.anosComConsultas.map((ano) => ano.year))
      : new Date().getFullYear();
  }
}
