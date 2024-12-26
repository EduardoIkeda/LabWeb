import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { data } from './../line-chart/sampledata';
import { Month } from '../../../shared/enum/month-enum';

@Component({
  selector: 'app-advance-pie-chart',
  standalone: true,
  imports: [
    NgxChartsModule,
    MatCardModule,
    MatSelectModule,
    MatFormFieldModule,
  ],
  templateUrl: './advance-pie-chart.component.html',
  styleUrl: './advance-pie-chart.component.scss',
})
export class AdvancePieChartComponent {
  @Input() data: any[] = data;
  @Input() title: string = 'Consultas por mês';
  @Input() months: number[] = Array.from({ length: 12 }, (_, i) => i + 1);
  @Input() selectedMonth: number = 12;
  @Output() monthChange = new EventEmitter<number>();
  view: [number, number] = [600, 250];

  onMonthChange(month: number): void {
    this.monthChange.emit(month);
  }

  getMonthName(month: number): string {
    return Month[month];
  }
}
