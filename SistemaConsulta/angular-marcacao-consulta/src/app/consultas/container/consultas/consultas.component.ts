import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import {
  MatCalendarCellClassFunction,
  MatDatepickerModule,
} from '@angular/material/datepicker';

import { ConsultaItemComponent } from '../../components/consulta-item/consulta-item.component';
import { Consulta } from '../../../shared/model/consulta';
import { ConsultasService } from './../../service/consultas.service';
import { firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../../shared/confirmation-dialog/confirmation-dialog.component';

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
  consultas: Consulta[] = [];

  constructor(
    private readonly consultasService: ConsultasService,
    private readonly router: Router,
    public dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.loadConsultas();
  }

  loadConsultas() {
    this.consultasService.listByUser().subscribe({
      next: (consultas) => {
        this.consultas = consultas.map(
          (consulta) =>
            new Consulta(
              consulta.id,
              consulta.appointmentDateTime,
              consulta.appointmentStatus,
              consulta.patientId,
              consulta.healthCenterId,
              consulta.specialtyId,
              consulta.doctorName,
              consulta.specialtyName,
              consulta.healthCenterName,
              consulta.healthCenterAddress,
              consulta.isTomorrow,
              consulta.isFinalized,
              consulta.postoId
            )
        );
        this.dateList = this.getDateList();
        console.log(this.consultas);
      },
      error: (error) => console.error('Error:', error),
    });
  }

  getDateList() {
    return this.consultas
      .map((consulta) => this.convertToDateTime(consulta.appointmentDateTime.toString()))
      .filter(
        (date) => date instanceof Date && date.getFullYear() === this.year
      );
  }

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

  onYearSelected(date: Date) {
    this.year = date.getFullYear();
    this.dateList = this.getDateList();
  }

  onMonthSelected(date: Date) {
    this.month = date.getMonth();
    this.dateList = this.getDateList();
  }

  onReschedule(consulta: Consulta) {
    this.router.navigate(['/consultas/reschedule', consulta.id]);
  }

  onCancel(consulta: Consulta) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: `Deseja realmente cancelar a consulta?

                  Especialidade: ${consulta.specialtyName}
                  Posto: ${consulta.healthCenterName}
                  Endereço: ${consulta.healthCenterAddress}
                  Profissional: ${consulta.doctorName}
                  Data e hora: ${consulta.appointmentDateTime}`,
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result && consulta != null) {
        consulta.patientId = null;

        this.consultasService.cancelarConsulta(consulta).subscribe({
          next: () => this.onSuccess(),
          error: (error) => this.onError(error),
        });
      }
    });
  }

  private onSuccess() {
    this.snackBar.open('Consulta cancelada com sucesso!', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });

    this.router.navigate(['/consultas']);
  }

  private onError(error: any) {
    console.error('Erro ao remarcar consulta:', error);

    this.snackBar.open('Erro ao cancelar a consulta. Tente novamente.', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }

  convertToDateTime(dateString: string): Date {
    const [datePart, timePart] = dateString.split(' ');
    const [day, month, year] = datePart.split('/');
    const [hours, minutes] = timePart.split(':');

    // Retorna o objeto Date com os componentes de data e hora
    return new Date(+year, +month - 1, +day, +hours, +minutes);
  }
}
