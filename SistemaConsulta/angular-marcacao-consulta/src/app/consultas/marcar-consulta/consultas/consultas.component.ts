import { Component, OnInit, Input, Output, EventEmitter, NgModule  } from '@angular/core';
import { ListaConsultas } from '../../model/lista_consultas';
import { ConsultasService } from './services/consultas.service';
import { CommonModule } from '@angular/common';
import { ConsultaPorData } from '../../model/consulta_por_data';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Consulta } from '../../../shared/model/consulta';
import { Especialidade } from '../../../shared/model/especialidade';
import { Posto } from '../../../shared/model/posto';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core'; // Para suporte nativo de datas
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule  } from '@angular/forms';

@Component({
  selector: 'app-consultas',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './consultas.component.html',
  styleUrl: './consultas.component.scss',
})
export class ConsultasComponent implements OnInit {
  listaConsultas!: ListaConsultas;
  listaPorData: ConsultaPorData[] = [];
  displayedListaPorData: ConsultaPorData[] = [];
  @Output() selectConsulta = new EventEmitter<Consulta>();
  @Input() speciality!: Especialidade | null;
  @Input() posto!: Posto | null;
  selectedDate: Date | null = null;

  constructor(private readonly consultasService: ConsultasService) {}

  ngOnInit(): void {
    this.loadConsultas();
  }

  loadConsultas() {
    this.consultasService.list(this.speciality!.id, this.posto!.id).subscribe({
      next: (data) => {
        this.listaConsultas = data;
        this.listaPorData = this.listaConsultas.listAppointmentsPerDate;
        this.displayedListaPorData = this.listaPorData;
      },
      error: (error) => console.error('Error:', error),
    });
  }

  onSelect(event: Event, consulta: Consulta) {
    event.stopPropagation();
    this.selectConsulta.emit(consulta);
  }

  onDateChange() {
    if (this.selectedDate) {
      // Zera a hora, minuto, segundo e milissegundo da data selecionada
      const resetSelectedDate = new Date(this.selectedDate.setHours(0, 0, 0, 0));

      this.displayedListaPorData = this.listaPorData.filter(data => {
        // Verifica se data.date é uma string e converte para um objeto Date
        const dataDate = new Date(data.date);

        // Zera a hora de dataDate para comparação
        const resetDataDate = new Date(dataDate.setHours(0, 0, 0, 0));
        return resetDataDate.getTime() === resetSelectedDate.getTime(); // Compara as datas sem as horas
      });
    } else {
      // Se nenhuma data estiver selecionada, mostra todas as consultas
      this.displayedListaPorData = this.listaPorData;
    }
  }



  limparData(){
    this.selectedDate = null;
    this.onDateChange();
  }
}
