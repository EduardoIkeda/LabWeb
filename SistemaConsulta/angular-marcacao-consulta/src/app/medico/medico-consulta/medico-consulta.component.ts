import { PostoService } from './../../shared/service/posto.service';
import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import {
  MatCalendarCellClassFunction,
  MatDatepickerModule,
} from '@angular/material/datepicker';
import { ActivatedRoute } from '@angular/router';
import { ConsultaItemComponent } from '../../consultas/components/consulta-item/consulta-item.component';
import { Consulta } from '../../shared/model/consulta';
import { ConsultasService } from '../../consultas/service/consultas.service';
import { Posto } from '../../shared/model/posto';
import { MatIconModule } from '@angular/material/icon';
import { UserService } from '../../shared/service/user.service';
import { DoctorService } from '../../shared/service/doctor.service';

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
  postos: Posto[] = [];
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
    private readonly postoService: PostoService,
    private readonly userService: UserService,
    private readonly doctorService: DoctorService,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const doctorId = this.route.snapshot.paramMap.get('id');
    if (doctorId) {
      this.loadPostos();
      this.loadDoctorName(doctorId);
    } else {
      console.error('ID do médico não encontrado na rota.');
    }
  }

  loadDoctorName(doctorId: string): void {
    this.doctorService.getDoctorById(doctorId).subscribe({
      next: (doctor) => {
        if (doctor) {
          this.doctorName = doctor.name;
          this.loadConsultas(doctor.name);
        }
      },
      error: (error) => console.error('Erro ao carregar o médico:', error),
    });
  }

  loadPostos(): void {
    this.postoService.list().subscribe({
      next: (postos) => {
        this.postos = postos;
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
          // Verificando se patientId é uma string válida
          const patientId = consulta.patientId;
          if (patientId && typeof patientId === 'string') {
            this.userService.docgetById(patientId).subscribe({
              next: (user) => {
                if (user) {
                  this.userNames[patientId] = user.name;
                }
              },
              error: (error) =>
                console.error('Erro ao carregar nome do paciente:', error),
            });
          }
        });
      },
      error: (error) => console.error('Erro ao carregar consultas:', error),
    });
  }

  getPostoName(postoId: string): string {
    if (!postoId) {
      return 'ID do posto não fornecido';
    }

    const posto = this.postos.find((posto) => posto.id === postoId);
    return posto ? posto.name : 'Posto não encontrado';
  }

  getPostoAddress(postoId: string): string {
    const posto = this.postos.find((posto) => posto.id === postoId);
    return posto ? posto.address : 'Endereço não disponível';
  }

  onYearSelected(date: Date): void {
    this.year = date.getFullYear();
  }

  onMonthSelected(date: Date): void {
    this.month = date.getMonth();
  }

  getPatientName(patientId: string | null): string {
    // Verificar se patientId é uma string válida antes de procurar no objeto
    if (typeof patientId === 'string') {
      const patientName = this.userNames[patientId];
      return patientName ? patientName : 'Paciente não encontrado';
    }
    return 'ID do paciente não fornecido';
  }
}
