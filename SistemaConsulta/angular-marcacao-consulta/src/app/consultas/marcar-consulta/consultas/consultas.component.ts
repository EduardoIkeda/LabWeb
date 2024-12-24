import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core'; // Para suporte nativo de datas
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

import { Consulta } from '../../../shared/model/consulta';
import { Especialidade } from '../../../shared/model/especialidade';
import { HealthCenter } from '../../../shared/model/health-center';
import { ConsultaPorData } from '../../model/consulta_por_data';
import { ConsultasService } from '../../service/consultas.service';

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
  listaPorData: ConsultaPorData[] = [];
  displayedListaPorData: ConsultaPorData[] = [];
  @Output() selectConsulta = new EventEmitter<Consulta>();
  @Input() speciality!: Especialidade | null;
  @Input() posto!: HealthCenter | null;
  selectedDate: Date | null = null;

  constructor(private readonly consultasService: ConsultasService) { }

  ngOnInit(): void {
    this.loadConsultas();
  }

  loadConsultas() {
    const specialityId = this.speciality?.id ?? '';
    const postoId = this.posto?.id ?? '';

    if (this.speciality && this.posto) {
      this.consultasService.listGroup(specialityId, postoId).subscribe({
        next: (data) => {
          this.listaPorData = data;
          this.displayedListaPorData = this.listaPorData;
          console.log(this.displayedListaPorData);
        },
        error: (error) => console.error('Error:', error),
      });
    } else {
      console.error('Especialidade ou Posto de Saúde não definidos.');
    }
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
        const dataDate = this.convertToDate(data.date.toString());

        // Zera a hora de dataDate para comparação
        const resetDataDate = new Date(dataDate.setHours(0, 0, 0, 0));
        return resetDataDate.getTime() === resetSelectedDate.getTime(); // Compara as datas sem as horas
      });
    } else {
      // Se nenhuma data estiver selecionada, mostra todas as consultas
      this.displayedListaPorData = this.listaPorData;
    }
  }



  limparData() {
    this.selectedDate = null;
    this.onDateChange();
  }

  convertToDateTime(dateString: string): Date {
    const [datePart, timePart] = dateString.split(' ');
    const [day, month, year] = datePart.split('/');
    const [hours, minutes] = timePart.split(':');

    // Retorna o objeto Date com os componentes de data e hora
    return new Date(+year, +month - 1, +day, +hours, +minutes);
  }

  convertToDate(dateString: string): Date {
    const [day, month, year] = dateString.split('/');

    // Retorna o objeto Date com os componentes de data
    return new Date(+year, +month - 1, +day);
  }
}
