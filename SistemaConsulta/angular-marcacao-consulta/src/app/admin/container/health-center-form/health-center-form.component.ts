import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { horarios } from '../../../../assets/horarios';
import { EspecialidadesService } from '../../../consultas/marcar-consulta/especialidades/services/especialidades.service';
import { Especialidade } from '../../../shared/model/especialidade';
import { HealthCenterService } from '../../../shared/service/health-center.service';

@Component({
  selector: 'app-health-center-form',
  standalone: true,
  imports: [
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatCardModule,
    CommonModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './health-center-form.component.html',
  styleUrl: './health-center-form.component.scss',
})
export class HealthCenterFormComponent implements OnInit {
  id: string = '';
  name: string = '';
  address: string = '';
  openingHour: string = '';
  closingHour: string = '';
  specialties: Especialidade[] = [];
  postoSaudeForm: FormGroup;
  horarios: string[] = horarios;

  constructor(
    private readonly fb: FormBuilder,
    private readonly location: Location,
    private readonly snackBar: MatSnackBar,
    private readonly route: ActivatedRoute,
    private readonly postoSaudeService: HealthCenterService,
    private readonly especialidadesService: EspecialidadesService
  ) {
    this.postoSaudeForm = this.fb.group(
      {
        id: [''],
        name: [
          '',
          [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(100),
          ],
        ],
        address: [
          '',
          [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(100),
          ],
        ],
        openingHour: ['', Validators.required],
        closingHour: ['', Validators.required],
        specialties: [[], Validators.required],
        availableAppointmentsCount: 0
      },
      {
        validators: this.hourRangeValidator,
      }
    );

    this.loadEspecialidades()
  }

  ngOnInit(): void {
    this.route.data.subscribe((data: any) => {
      if (data.postoSaude) {
        this.postoSaudeForm.patchValue(data.postoSaude);
      }
    });
  }

  loadEspecialidades() {
    this.especialidadesService.list().subscribe({
      next: (especialidades) => {
        this.specialties = especialidades
        console.log(this.specialties)
      },
      error: (error) => console.error('Error:', error),
    });
  }

  hourRangeValidator(formGroup: FormGroup) {
    const openingHour = formGroup.get('openingHour')?.value;
    const closingHour = formGroup.get('closingHour')?.value;

    return openingHour && closingHour && openingHour < closingHour
      ? null
      : { invalidHours: true };
  }

  onSubmit() {
    if (this.postoSaudeForm.valid) {
      this.postoSaudeService.save(this.postoSaudeForm.value);
    } else if (this.postoSaudeForm.errors?.['invalidHours']) {
      this.invalidMessage('Horário de abertura deve ser menor que o horário de fechamento');
    } else {
      this.invalidMessage('Formulário inválido');
    }
  }

  invalidMessage(message: string) {
    this.snackBar.open(message, 'Fechar', {
      duration: 3000,
    });
  }

  onCancel() {
    this.location.back();
  }
}
