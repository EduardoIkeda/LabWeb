import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCalendarCellClassFunction, MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';

import { ConsultaItemComponent } from '../../consultas/components/consulta-item/consulta-item.component';
import { ConsultasService } from '../../consultas/service/consultas.service';
import { Consulta } from '../../shared/model/consulta';
import { HealthCenter } from '../../shared/model/health-center';
import { DoctorService } from '../../shared/service/doctor.service';
import { HealthCenterService } from '../../shared/service/health-center.service';
import { UserService } from '../../shared/service/user.service';

@Component({
  selector: 'app-medico-consulta',
  standalone: true,
  imports: [
    ConsultaItemComponent,
    MatCardModule,
    MatNativeDateModule,
    MatDatepickerModule,
    CommonModule,
    MatIconModule,
  ],
  templateUrl: './medico-consulta.component.html',
  styleUrls: ['./medico-consulta.component.scss'],
})
export class MedicoConsultaComponent implements OnInit {
  year: number = new Date().getFullYear();
  month: number = new Date().getMonth();
  dateList: Date[] = [];
  consultas: Consulta[] = [];
  healthCenters: HealthCenter[] = [];
  doctorName: string = '';
  userNames: { [key: string]: string } = {};
  @Input() consulta!: Consulta;
  @Output() reschedule = new EventEmitter<Consulta>();
  @Output() cancel = new EventEmitter<Consulta>();
  dateClass: MatCalendarCellClassFunction<Date> = (cellDate, view) => {
    if (view === 'month') {
      return this.dateList.some(
        (date) =>
          date.getDate() === cellDate.getDate() &&
          date.getMonth() === cellDate.getMonth() &&
          date.getFullYear() === cellDate.getFullYear()
      )
        ? 'example-custom-date-class'
        : '';
    }
    return '';
  };

  constructor(
    private readonly consultasService: ConsultasService,
    private readonly healthCenterService: HealthCenterService,
    private readonly userService: UserService,
    private readonly doctorService: DoctorService,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const doctorId = this.route.snapshot.paramMap.get('id');
    if (doctorId) {
      this.loadHealthCenters();
      this.loadDoctorName(doctorId);
    } else {
      console.error('ID do médico não encontrado na rota.');
    }
  }

  loadDoctorName(doctorId: string): void {
    this.doctorService.getDoctorById(doctorId).subscribe({
      next: (doctor) => {
        if (doctor) {
          this.doctorName = doctor.doctorName;
          this.loadConsultas(doctor.doctorName);
        }
      },
      error: (error) => console.error('Erro ao carregar o médico:', error),
    });
  }

  loadHealthCenters(): void {
    this.healthCenterService.list().subscribe({
      next: (healthCenters) => {
        this.healthCenters = healthCenters;
      },
      error: (error) => console.error('Erro ao carregar postos:', error),
    });
  }
  loadConsultas(doctorName: string): void {
    this.consultasService.listByDoctor(doctorName).subscribe({
      next: (consultas) => {
        this.consultas = consultas;
        this.dateList = consultas.map(
          (consulta) => new Date(consulta.appointmentDateTime)
        );

        consultas.forEach((consulta) => {
          console.log('Consulta:', consulta);

          const patientId = consulta.patientId;
          if (patientId && typeof patientId === 'string') {
            this.userService.docgetById(patientId).subscribe({
              next: (user) => {
                if (user) {
                  this.userNames[patientId] = user.name;
                }
              },
              error: (error) => console.error('Erro ao carregar nome do paciente:', error),
            });
          }
        });
      },
      error: (error) => console.error('Erro ao carregar consultas:', error),
    });
  }


  getHealthCenterName(healthCenterId: string): string {
    if (!healthCenterId) {
      return 'ID do posto não fornecido';
    }

    const healthCenter = this.healthCenters.find((healthCenter) => healthCenter.id === healthCenterId);
    return healthCenter ? healthCenter.name : 'Posto não encontrado';
  }

  getHealthCenterAddress(healthCenterId: string): string {
    const healthCenter = this.healthCenters.find((healthCenter) => healthCenter.id === healthCenterId);
    return healthCenter ? healthCenter.address : 'Endereço não disponível';
  }

  onYearSelected(date: Date): void {
    this.year = date.getFullYear();
  }

  onMonthSelected(date: Date): void {
    this.month = date.getMonth();
  }

  getPatientName(patientId: string | null): string {
    if (typeof patientId === 'string') {
      const patientName = this.userNames[patientId];
      return patientName ? patientName : 'Paciente não encontrado';
    }
    return 'ID do paciente não fornecido';
  }
}
