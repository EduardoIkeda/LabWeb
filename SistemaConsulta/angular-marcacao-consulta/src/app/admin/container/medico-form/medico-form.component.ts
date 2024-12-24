import { DoctorService } from './../../../shared/service/doctor.service';
import { HealthCenterService } from './../../../shared/service/health-center.service';
import { CommonModule, Location } from '@angular/common';
import { HealthCenter } from '../../../shared/model/health-center';
import { Component, OnInit } from '@angular/core';
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
export class MedicoFormComponent implements OnInit{
  healthCenter: HealthCenter = {
    id: '',
    name: '',
    address: '',
    openingHour: '',
    closingHour: '',
    specialties: [],
    availableAppointmentsCount: 0
  };

  allocatedDoctorSearchTerm: string = '';
  allocatedDoctorsFiltered: Doctor[] = [];
  allocatedDoctors: Doctor[] = [];

  registeredDoctorSearchTerm: string = '';
  registeredDoctorsFiltered: Doctor[] = [];
  registeredDoctors: Doctor[] = [];

  constructor(
    private readonly postoSaudeService: HealthCenterService,
    private readonly route: ActivatedRoute,
    private readonly location: Location,
    private readonly doctorService: DoctorService
  ) {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.postoSaudeService.loadById(id).subscribe((healthCenter) => {
          this.healthCenter = healthCenter;
        });
      }
    });
  }

  ngOnInit(): void {
    this.doctorService.list().subscribe((doctors) => {
      this.registeredDoctors = doctors;
      this.registeredDoctorsFiltered = [...this.registeredDoctors];
    });

    this.allocatedDoctors = [];
  }

  onRemove(medico: Doctor) {
    this.updateLists(medico, 'remove');
  }

  onAdd(medico: Doctor) {
    this.updateLists(medico, 'add');
  }

  private updateLists(medico: Doctor, action: 'add' | 'remove') {
    if (action === 'add') {
      this.allocatedDoctors.push(medico);
      this.registeredDoctors = this.registeredDoctors.filter(
        (m) => m.id !== medico.id
      );
      this.registeredDoctorsFiltered = this.registeredDoctors;
      this.allocatedDoctorsFiltered = this.allocatedDoctors;
    } else if (action === 'remove') {
      this.registeredDoctors.push(medico);
      this.allocatedDoctors = this.allocatedDoctors.filter(
        (m) => m.id !== medico.id
      );
      this.registeredDoctorsFiltered = this.registeredDoctors;
      this.allocatedDoctorsFiltered = this.allocatedDoctors;
    }
  }

  filterMedicosCadastrados() {
    const term = this.registeredDoctorSearchTerm.toLowerCase();
    if (!term) {
      this.registeredDoctorsFiltered = [...this.registeredDoctors];
      return;
    }

    this.registeredDoctorsFiltered = this.filterMedicos(
      this.registeredDoctors,
      term
    );
  }

  filterMedicosAlocados() {
    const term = this.allocatedDoctorSearchTerm.toLowerCase();
    if (!term) {
      this.allocatedDoctorsFiltered = [...this.allocatedDoctors];
      return;
    }

    this.allocatedDoctorsFiltered = this.filterMedicos(
      this.allocatedDoctors,
      term
    );
  }

  private filterMedicos(medicos: Doctor[], term: string) {
    return medicos.filter((medico) => {
      return (
        medico.doctorName.toLowerCase().includes(term) || medico.crm.includes(term)
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
