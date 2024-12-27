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

  ngOnInit(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      this.avatarUrl = localStorage.getItem("userAvatarUrl");
    }

    const doctorId = this.route.snapshot.paramMap.get('id');
    console.log('ID do médico:', doctorId);

    if (doctorId) {
      this.loadDoctorAndSpecialties(doctorId);
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
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = 'Erro ao carregar o perfil do médico.';
        console.error('Erro:', err);
        this.isLoading = false;
      },
    });
  }

  private loadDoctorAndSpecialties(id: string): void {
    this.isLoading = true;

    this.doctorService.getSpecialties().subscribe({
      next: (specialties) => {
        this.specialties = specialties;

        this.doctorService.getDoctorById(id).subscribe({
          next: (doctor) => {
            // Mapear IDs das especialidades para seus nomes
            doctor.specialties = doctor.specialties.map((especialidade) => {
              const specialty = this.specialties.find(
                (s) => s.id === especialidade.id
              );
              return {
                ...especialidade,
                name: specialty?.name ?? 'Desconhecida'
              };
            });
            this.doctor = doctor;
            this.isLoading = false;
          },
          error: (err) => {
            this.errorMessage = 'Erro ao carregar o perfil do médico.';
            console.error('Erro:', err);
            this.isLoading = false;
          },
        });

      },
      error: (err) => {
        this.errorMessage = 'Erro ao carregar especialidades.';
        console.error('Erro:', err);
        this.isLoading = false;
      },
    });
  }
}