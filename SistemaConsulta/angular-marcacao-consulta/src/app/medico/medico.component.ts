import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { Doctor } from '../shared/model/doctor';
import { DoctorService } from '../shared/service/doctor.service';
import { ActivatedRoute } from '@angular/router';
import { Especialidade } from '../shared/model/especialidade';

@Component({
  selector: 'app-medico',
  standalone: true,
  imports: [
      MatInputModule,
      CommonModule,
      MatCardModule,
      MatButtonModule,
      FormsModule,
      ReactiveFormsModule],
  templateUrl: './medico.component.html',
  styleUrl: './medico.component.scss'
})
export class MedicoComponent implements OnInit {
  doctor!: Doctor;
  isLoading = true;
  errorMessage: string | null = null;
  specialties: Especialidade[] = [];


  constructor(
    private readonly doctorService: DoctorService,
    private readonly route: ActivatedRoute
  ) {}
  ngOnInit(): void {
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
      }
    });
  } private loadDoctorAndSpecialties(id: string): void {
    this.isLoading = true;
    this.doctorService.getSpecialties().subscribe({
      next: (specialties) => {
        this.specialties = specialties;
        this.doctorService.getDoctorById(id).subscribe({
          next: (doctor) => {
            // Mapear IDs das especialidades para seus nomes
            doctor.especialidade = doctor.especialidade.map(especialidade => {
              const specialty = this.specialties.find(s => s.id === especialidade.id);
              return {
                ...especialidade,
                name: specialty?.name || 'Desconhecida'
              };
            });
            this.doctor = doctor;
            this.isLoading = false;
          },
          error: (err) => {
            this.errorMessage = 'Erro ao carregar o perfil do médico.';
            console.error('Erro:', err);
            this.isLoading = false;
          }
        });
      },
      error: (err) => {
        this.errorMessage = 'Erro ao carregar especialidades.';
        console.error('Erro:', err);
        this.isLoading = false;
      }
    });
  }
  get avatarUrl(): string {
    return (
      this.doctor?.avatarUrl ||
      'https://material.angular.io/assets/img/examples/shiba1.jpg'
    );
  }
}
