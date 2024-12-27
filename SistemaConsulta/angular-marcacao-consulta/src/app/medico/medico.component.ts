import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute } from '@angular/router';

import { Doctor } from '../shared/model/doctor';
import { Especialidade } from '../shared/model/especialidade';
import { DoctorService } from '../shared/service/doctor.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-medico',
  standalone: true,
  imports: [
    MatInputModule,
    CommonModule,
    MatCardModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './medico.component.html',
  styleUrl: './medico.component.scss',
})
export class MedicoComponent implements OnInit {
  doctor!: Doctor;
  isLoading = true;
  errorMessage: string | null = null;
  specialties: Especialidade[] = [];
  avatarUrl: string | null = '';

  constructor(
    private readonly doctorService: DoctorService,
    private readonly route: ActivatedRoute
  ) { }

  async ngOnInit(): Promise<void> {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      this.avatarUrl = localStorage.getItem("userAvatarUrl");
    }

    const userId = this.route.snapshot.paramMap.get('id');
    const doctorId = await firstValueFrom(this.doctorService.getDoctorIdByUserId(userId));

    console.log('ID do médico:', doctorId);

    if (doctorId) {
      this.loadDoctor(doctorId);
    } else {
      this.errorMessage = 'ID do médico não foi fornecido na URL.';
      console.error(this.errorMessage);
    }
  }

  private loadDoctor(id: string): void {
    this.isLoading = true;

    this.doctorService.getDoctorById(id).subscribe({
      next: (doctor) => {
        this.doctor = doctor;
        this.specialties = doctor.specialties;
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = 'Erro ao carregar o perfil do médico.';
        console.error('Erro:', err);
        this.isLoading = false;
      },
    });
  }
}
