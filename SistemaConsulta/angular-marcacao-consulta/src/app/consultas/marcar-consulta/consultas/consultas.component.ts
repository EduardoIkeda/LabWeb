import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ListaConsultas } from '../../model/lista_consultas';
import { ConsultasService } from './services/consultas.service';
import { CommonModule } from '@angular/common';
import { ConsultaPorData } from '../../model/consulta_por_data';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Consulta } from '../../../shared/model/consulta';

@Component({
  selector: 'app-consultas',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, MatCardModule],
  templateUrl: './consultas.component.html',
  styleUrl: './consultas.component.scss'
})
export class ConsultasComponent implements OnInit{
  listaConsultas!: ListaConsultas;
  listaPorData: ConsultaPorData[] = [];
  @Output() selectConsulta = new EventEmitter<Consulta>();

  constructor(private readonly consultasService: ConsultasService) {}

  ngOnInit(): void {
    this.loadConsultas();
  }

  loadConsultas() {
    this.consultasService.list().subscribe({
      next: (data) => {
        this.listaConsultas = data;
        this.listaPorData = this.listaConsultas.listAppointmentsPerDate;
      },
      error: (error) => console.error('Error:', error),
    });
  }

  onSelect(event: Event, consulta: Consulta){
    event.stopPropagation();
    this.selectConsulta.emit(consulta);
  }
}
