import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCalendarCellClassFunction, MatDatepickerModule } from '@angular/material/datepicker';

import { ConsultaItemComponent } from '../../components/consulta-item/consulta-item.component';
import { Consulta } from '../../../shared/model/consulta';
import { ConsultasService } from './../../service/consultas.service';

@Component({
  selector: 'app-consultas',
  standalone: true,
  imports: [
    ConsultaItemComponent,
    MatCardModule,
    MatNativeDateModule,
    MatDatepickerModule,
    CommonModule,
  ],
  templateUrl: './consultas.component.html',
  styleUrls: ['./consultas.component.scss'],
})
export class ConsultasComponent {
  year: number = new Date().getFullYear();
  month: number = new Date().getMonth();
  dateList: Date[] = [];

  dateClass: MatCalendarCellClassFunction<Date> = (cellDate, view) => {
    this.dateList = this.getDateList();
    return this.getDateClass(cellDate, view);
  };

  getDateClass(cellDate: Date, view: string): string {
    if (view === 'month') {
      const date = cellDate.getDate();
      const month = cellDate.getMonth();
      const year = cellDate.getFullYear();

      const list = this.dateList.some(
        (d) =>
          d.getDate() === date &&
          d.getMonth() === month &&
          d.getFullYear() === year
      );

      return list ? 'example-custom-date-class' : '';
    }

    return '';
  }

  consultas: Consulta[] = [];

  constructor(private readonly consultasService: ConsultasService) {
    this.loadConsultas();
  }

  loadConsultas() {
    this.consultasService.list().subscribe({
      next: (consultas) => {
        this.consultas = consultas.map((consulta) => ({
          ...consulta,
          date: new Date(consulta.date),
        }));
        this.dateList = this.getDateList();
      },
      error: (error) => console.error('Error:', error),
    });
  }

  getDateList() {
    return this.consultas
      .map((consulta) => consulta.date)
      .filter(
        (date) =>
          date.getFullYear() === this.year
      );
  }

  onYearSelected(date: Date) {
    this.year = date.getFullYear();
    this.dateList = this.getDateList();
  }

  onMonthSelected(date: Date) {
    this.month = date.getMonth();
    this.dateList = this.getDateList();
  }

  onReschedule(consulta: Consulta) {
    console.log('Reschedule id:', consulta);
  }

  onCancel(consulta: Consulta) {
    console.log('Cancel id:', consulta);
  }
}
