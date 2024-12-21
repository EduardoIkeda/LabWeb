import { PostoSaudeService } from './../../../shared/service/posto-saude.service';
import { horarios } from './../../../../assets/horarios';
import { Component, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule, Location } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { HealthCenter } from '../../../../app/shared/model/posto-saude';

@Component({
  selector: 'app-posto-saude-form',
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
  templateUrl: './posto-saude-form.component.html',
  styleUrl: './posto-saude-form.component.scss',
})
export class PostoSaudeFormComponent implements OnInit {
  name: string = '';
  address: string = '';
  openingHour: string = '';
  closingHour: string = '';
  specialities: string[] = [
    'Cardiologia',
    'Dermatologia',
    'Pediatria',
    'Ortopedia',
  ];
  postoSaudeForm: FormGroup;
  horarios: string[] = horarios;

  constructor(
    private readonly fb: FormBuilder,
    private readonly location: Location,
    private readonly snackBar: MatSnackBar,
    private readonly route: ActivatedRoute,
    private readonly postoSaudeService: PostoSaudeService
  ) {
    this.postoSaudeForm = this.fb.group(
      {
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
        specialities: [[], Validators.required],
      },
      {
        validators: this.hourRangeValidator,
      }
    );
  }

  ngOnInit(): void {
    this.route.data.subscribe((data: any) => {
      if (data.postoSaude) {
        this.postoSaudeForm.patchValue(data.postoSaude);
      }
    });
  }

  hourRangeValidator(formGroup: FormGroup) {
    const openingHour = formGroup.get('openingHour')?.value;
    const closingHour = formGroup.get('closingHour')?.value;

    return openingHour &&
      closingHour &&
      openingHour < closingHour
      ? null
      : { invalidHours: true };
  }

  onSubmit() {
    if (this.postoSaudeForm.valid) {
      this.postoSaudeService.save(this.postoSaudeForm.value);
    } else if (this.postoSaudeForm.errors?.['invalidHours']) {
      this.invalidMessage(
        'Horário de abertura deve ser menor que o horário de fechamento'
      );
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
