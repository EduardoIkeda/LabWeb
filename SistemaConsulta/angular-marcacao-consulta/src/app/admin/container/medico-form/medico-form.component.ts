import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatListModule } from '@angular/material/list';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';

import { Doctor } from '../../../shared/model/doctor';
import { HealthCenter } from '../../../shared/model/health-center';
import { DoctorService } from './../../../shared/service/doctor.service';
import { HealthCenterService } from './../../../shared/service/health-center.service';

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
    doctors: [],
    availableAppointmentsCount: 0
  };

  healthCenterId: string | null = '';

  allocatedDoctorSearchTerm: string = '';
  allocatedDoctorsFiltered: Doctor[] = [];
  allocatedDoctors: Doctor[] = [];

  registeredDoctorSearchTerm: string = '';
  registeredDoctorsFiltered: Doctor[] = [];
  registeredDoctors: Doctor[] = [];

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly location: Location,
    private readonly snackBar: MatSnackBar,
    private readonly postoSaudeService: HealthCenterService,
    private readonly doctorService: DoctorService
  ) {

  }

  async ngOnInit(): Promise<void> {
    const params = await firstValueFrom(this.route.paramMap);
    this.healthCenterId = params.get('id');

    if (this.healthCenterId) {
      this.healthCenter = await firstValueFrom(this.postoSaudeService.loadById(this.healthCenterId));

      this.allocatedDoctors = await firstValueFrom(this.doctorService.listByHealthCenter(this.healthCenterId));
      this.allocatedDoctorsFiltered = [...this.allocatedDoctors];

      this.registeredDoctors = await firstValueFrom(this.doctorService.list());
      this.registeredDoctors = this.registeredDoctors.filter(
        doctor => !this.allocatedDoctors.some(allocated => allocated.id === doctor.id)
      );
      this.registeredDoctorsFiltered = [...this.registeredDoctors];
    }
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

  onCancel() {
    this.location.back();
  }

  onSave() {
    if (this.healthCenter) {
      this.healthCenter.doctors = [];
      this.healthCenter.doctors.push(...this.allocatedDoctors);

      this.postoSaudeService.addDoctors(this.healthCenter).subscribe({
        next: () => this.onSuccess(),
        error: (error) => this.onError(error)
      });
    }
  }

  private onSuccess() {
    this.snackBar.open('Médicos alterados com sucesso!', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
    this.router.navigate(['/admin/posto-saude']);
  }

  private onError(error: any) {
    console.error('Erro ao marcar consulta:', error);

    this.snackBar.open('Erro ao alterar os médicos.', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
}
