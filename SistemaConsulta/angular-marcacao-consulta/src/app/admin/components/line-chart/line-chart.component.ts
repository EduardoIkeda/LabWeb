import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Color, NgxChartsModule, ScaleType } from '@swimlane/ngx-charts';

import { data } from './sampledata';

@Component({
  selector: 'app-line-chart',
  standalone: true,
  imports: [NgxChartsModule, MatCardModule],
  templateUrl: './line-chart.component.html',
  styleUrl: './line-chart.component.scss',
})
export class LineChartComponent {
  @Input() data: any[] = data;
  @Input() title: string = 'Consultas por mês';
  @Input() yAxisLabel: string = 'Número de consultas';
  @Input() xAxisLabel: string = 'Meses';
  @Input() view: [number, number] = [700, 400];

  // options
  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  timeline: boolean = true;

  colorScheme: string | Color = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5'],
    name: 'default',
    selectable: true,
    group: ScaleType.Ordinal,
  };
}
