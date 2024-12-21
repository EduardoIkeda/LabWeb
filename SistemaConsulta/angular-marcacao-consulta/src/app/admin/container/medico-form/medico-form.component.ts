import { CommonModule } from '@angular/common';
import { HealthCenter } from './../../../shared/model/posto-saude';
import { Component } from '@angular/core';
import { Medico } from '../../../shared/model/medico';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-medico-form',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatListModule,
    FormsModule,
    MatFormFieldModule,
  ],
  templateUrl: './medico-form.component.html',
  styleUrl: './medico-form.component.scss',
})
export class MedicoFormComponent {
  postoSaude: HealthCenter = {
    id: '1',
    name: 'Posto de Saúde 1',
    address: 'Rua 1, 123',
    openingHour: '08:00',
    closingHour: '17:00',
    // especialidades: ['Cardiologia', 'Dermatologia', 'Pediatria', 'Ortopedia'],
  };

  medicos: Medico[] = [
    {
      id: '1',
      name: 'João',
      crm: '123456',
      appointments: [],
    },
    {
      id: '2',
      name: 'Maria',
      crm: '654321',
      appointments: [],
    },
  ];

  medicosAlocados: Medico[] = [];
  medicosCadastrados: Medico[] = [...this.medicos]; // Use spread operator to create a new array

  searchTermAlocado: string = '';
  searchTermCadastrado: string = '';
  filteredMedicosCadastrados: Medico[] = [];
  filteredMedicosAlocados: Medico[] = [];

  constructor() {
    this.filteredMedicosCadastrados = [...this.medicosCadastrados];
  }

  onRemove(medico: Medico) {
    this.updateLists(medico, 'remove');
  }

  onAdd(medico: Medico) {
    this.updateLists(medico, 'add');
  }

  private updateLists(medico: Medico, action: 'add' | 'remove') {
    if (action === 'add') {
      this.medicosAlocados.push(medico);
      this.medicosCadastrados = this.medicosCadastrados.filter(
        (m) => m.id !== medico.id
      );
      this.filteredMedicosCadastrados = [...this.medicosCadastrados];
    } else if (action === 'remove') {
      this.medicosCadastrados.push(medico);
      this.medicosAlocados = this.medicosAlocados.filter(
        (m) => m.id !== medico.id
      );
      this.filteredMedicosCadastrados = [...this.medicosCadastrados];
    }
  }

  filterMedicosCadastrados() {
    const term = this.searchTermCadastrado.toLowerCase();
    if (!term) {
      this.filteredMedicosCadastrados = [...this.medicosCadastrados];
      return;
    }

    this.filteredMedicosCadastrados = this.filterMedicos(this.medicosCadastrados, term);
  }

  filterMedicosAlocados() {
    const term = this.searchTermAlocado.toLowerCase();
    if (!term) {
      this.filteredMedicosAlocados = [...this.medicosAlocados];
      return;
    }

    this.filteredMedicosAlocados = this.filterMedicos(
      this.medicosAlocados,
      term
    );
  }

  private filterMedicos(medicos: Medico[], term: string) {
    return medicos.filter((medico) => {
      return (
        medico.name.toLowerCase().includes(term) || medico.crm.includes(term)
      );
    });
  }


  onSave() {
    console.log('Save');
  }

  onCancel() {
    console.log('Cancel');
  }
}
