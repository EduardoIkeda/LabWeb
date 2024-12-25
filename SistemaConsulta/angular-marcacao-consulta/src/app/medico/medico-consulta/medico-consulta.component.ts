import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCalendarCellClassFunction, MatDatepickerModule } from '@angular/material/datepicker';
import { ActivatedRoute } from '@angular/router';
import { ConsultaItemComponent } from '../../consultas/components/consulta-item/consulta-item.component';
import { Consulta } from '../../shared/model/consulta';
import { ConsultasService } from '../../consultas/service/consultas.service';
import { PostoService } from '../../shared/service/posto.service';
import { Posto } from '../../shared/model/posto';
import { MatIconModule } from '@angular/material/icon';

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
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const doctorId = this.route.snapshot.paramMap.get('id');
    if (doctorId) {
      this.loadPostos();
      this.loadConsultas(doctorId);
    }
  }

  loadPostos(): void {
    this.postoService.list().subscribe({
      next: (postos) => {
        this.postos = postos;
      },
      error: (error) => console.error('Erro ao carregar postos:', error),
    });
  }

  loadConsultas(doctorId: string): void {
    this.consultasService.list().subscribe({
      next: (consultas) => {
        this.consultas = consultas.filter((consulta) => consulta.doctorId === doctorId);
        this.dateList = this.consultas.map((consulta) => new Date(consulta.appointmentDateTime));
      },
      error: (error) => console.error('Erro ao carregar consultas:', error),
    });
  }

  getPostoName(postoId: string): string {
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

  onReschedule(event: Event): void {
    event.stopPropagation();
    this.reschedule.emit(this.consulta);
  }

  onCancel(event: Event): void {
    event.stopPropagation();
    this.cancel.emit(this.consulta);
  }
}
