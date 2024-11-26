// consultas.component.ts
import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import {
  MatNativeDateModule,
  provideNativeDateAdapter,
} from '@angular/material/core';
import {
  MatCalendarCellClassFunction,
  MatDatepickerModule,
} from '@angular/material/datepicker';

import { Consulta } from '../model/consulta';
import { ConsultaItemComponent } from './consulta-item/consulta-item.component';

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
  dateClass: MatCalendarCellClassFunction<Date> = (cellDate, view) => {
    // Only highligh dates inside the month view.
    if (view === 'month') {
      const date = cellDate.getDate();

      return date === 1 || date === 25 ? 'example-custom-date-class' : '';
    }

    return '';
  };

  consultas: Consulta[] = [
    new Consulta(
      '1',
      'João',
      'Dr. Carlos',
      new Date(2021, 11, 25, 10, 30),
      'Cardiologia'
    ),
    new Consulta(
      '2',
      'Maria',
      'Dr. Ana',
      new Date(2024, 11, 1, 20, 0),
      'Oftalmologia'
    ),
    new Consulta(
      '3',
      'José',
      'Dr. Pedro',
      new Date(2024, 11, 26, 8, 0),
      'Pediatria'
    ),
  ];

  constructor() {}

  onReschedule(consulta: Consulta) {
    console.log('Reschedule id:', consulta);
  }

  onCancel(consulta: Consulta) {
    console.log('Cancel id:', consulta);
  }
}
