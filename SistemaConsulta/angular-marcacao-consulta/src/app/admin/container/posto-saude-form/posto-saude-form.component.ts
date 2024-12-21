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
import { PostoSaude } from '../../../../app/shared/model/posto-saude';

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
  nome: string = '';
  endereco: string = '';
  horarioAbertura: string = '';
  horarioFechamento: string = '';
  especialidades: string[] = [
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
    private readonly route: ActivatedRoute
  ) {
    this.postoSaudeForm = this.fb.group(
      {
        nome: [
          '',
          [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(100),
          ],
        ],
        endereco: [
          '',
          [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(100),
          ],
        ],
        horarioAbertura: ['', Validators.required],
        horarioFechamento: ['', Validators.required],
        especialidades: [[], Validators.required],
      },
      {
        validators: this.horarioValidator,
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

  horarioValidator(formGroup: FormGroup) {
    const horarioAbertura = formGroup.get('horarioAbertura')?.value;
    const horarioFechamento = formGroup.get('horarioFechamento')?.value;

    return horarioAbertura &&
      horarioFechamento &&
      horarioAbertura < horarioFechamento
      ? null
      : { horarioInvalido: true };
  }

  onSubmit() {
    if (this.postoSaudeForm.valid) {
      console.log(this.postoSaudeForm.value);
    } else if (this.postoSaudeForm.errors?.['horarioInvalido']) {
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
