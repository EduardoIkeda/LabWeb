import { HealthCenterService } from './../../../shared/service/health-center.service';
import { CommonModule, Location } from '@angular/common';
import { HealthCenter } from '../../../shared/model/health-center';
import { Component } from '@angular/core';
import { Doctor } from '../../../shared/model/doctor';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ActivatedRoute } from '@angular/router';

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
  healthCenter: HealthCenter = {
    id: '',
    name: '',
    address: '',
    openingHour: '',
    closingHour: ''
  };

  medicos: Doctor[] = [
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

  medicosAlocados: Doctor[] = [];
  medicosCadastrados: Doctor[] = [...this.medicos];

  searchTermAlocado: string = '';
  searchTermCadastrado: string = '';
  filteredMedicosCadastrados: Doctor[] = [];
  filteredMedicosAlocados: Doctor[] = [];

  constructor(
    private readonly postoSaudeService: HealthCenterService,
    private readonly route: ActivatedRoute,
    private readonly location: Location
  ) {
    this.filteredMedicosCadastrados = [...this.medicosCadastrados];
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.postoSaudeService.loadById(id).subscribe((healthCenter) => {
          this.healthCenter = healthCenter;
        });
      }
    });
  }

  onRemove(medico: Doctor) {
    this.updateLists(medico, 'remove');
  }

  onAdd(medico: Doctor) {
    this.updateLists(medico, 'add');
  }

  private updateLists(medico: Doctor, action: 'add' | 'remove') {
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

    this.filteredMedicosCadastrados = this.filterMedicos(
      this.medicosCadastrados,
      term
    );
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

  private filterMedicos(medicos: Doctor[], term: string) {
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
    this.location.back();
  }
}
