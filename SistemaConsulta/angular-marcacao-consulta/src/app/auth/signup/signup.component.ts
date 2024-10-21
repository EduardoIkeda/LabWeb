import { Component, OnInit } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import {
  FormBuilder,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
  AbstractControl,
  ValidationErrors,
  ValidatorFn,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    CommonModule,
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent implements OnInit {
  form: FormGroup;
  constructor(
    private formBuilder: NonNullableFormBuilder,
    private snackBar: MatSnackBar
  ) {
    this.form = this.formBuilder.group({
      id: [''],
      sus: [
        '',
        [
          Validators.required,
          Validators.minLength(15),
          Validators.maxLength(15),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirm: ['', [Validators.required]],
      name: [
        '',
        [Validators.required, Validators.minLength(5), Validators.maxLength(50)],
      ],
      cpf: [
        '',
        [
          Validators.required,
          Validators.minLength(11),
          Validators.maxLength(11),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
      phone: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(12),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.maxLength(100)
        ],
      ],
    },
    {
      validators: this.confirmPasswordValidator,
    }
  );
  }

  confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const password = control.get('password')?.value;
    const passwordConfirm = control.get('passwordConfirm')?.value;
    return password && passwordConfirm && password !== passwordConfirm
    ? { PasswordNoMatch: true }
    : null;
  };

  onSubmit() {
    alert('cadastrou');
  }

  errorMessage(fieldName: string) {
    const field = this.form.get(fieldName);
    if (field?.hasError('required')) {
      return 'Campo obrigatório';
    }
    if (field?.hasError('pattern')) {
      return `Digite apenas números`;
    }
    if (field?.hasError('email')) {
      return `Email inválido`;
    }
    if (field?.hasError('minlength')) {
      const requiredLength: number = field.errors
        ? field.errors['minlength']['requiredLength']
        : 8;
      return `Tamanho mínimo precisa ser de ${requiredLength} caracteres`;
    }
    if (field?.hasError('maxlength')) {
      const requiredLength: number = field.errors
        ? field.errors['maxlength']['requiredLength']
        : 15;
      return `Tamanho máximo excedido de ${requiredLength} caracteres`;
    }
    return 'Campo inválido';
  }

  ngOnInit(): void {}
}