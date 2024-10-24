import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { FormUtilsService } from '../../shared/form/form-utils.service';
import { UsersService } from '../services/users.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSnackBarModule
],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent implements OnInit {
  form: FormGroup;
  constructor(
    private service: UsersService,
    private formBuilder: NonNullableFormBuilder,
    private snackBar: MatSnackBar,
    private location: Location,
    public formUtils: FormUtilsService
  ) {
    this.form = this.formBuilder.group({
      id: [''],
      sus: ['',
        [Validators.required,
        Validators.minLength(15),
        Validators.maxLength(15),
        Validators.pattern('^[0-9]*$')]],
      name: ['',
        [Validators.required,
        Validators.minLength(5),
        Validators.maxLength(50)],
      ],
      cpf: ['',
        [Validators.required,
        Validators.minLength(11),
        Validators.maxLength(11),
        Validators.pattern('^[0-9]*$')]],
      phone: ['',
        [Validators.required,
        Validators.minLength(10),
        Validators.maxLength(12),
        Validators.pattern('^[0-9]*$')]],
      email: ['',
        [Validators.required,
        Validators.email,
        Validators.maxLength(100)]],
      password: ['',
        [Validators.required,
        Validators.minLength(8)]],
      confirmPassword: ['',
        [Validators.required,
        this.validateSamePassword]]
    });
  }

  ngOnInit(): void {
    this.form.get('password')?.valueChanges.subscribe(() => {
      this.form.updateValueAndValidity();
    });

    this.form.get('passwordConfirm')?.valueChanges.subscribe(() => {
      this.form.updateValueAndValidity();
    });
  }

  private validateSamePassword(control: AbstractControl): ValidationErrors | null {
    const password = control.parent?.get('password');
    const confirmPassword = control.parent?.get('confirmPassword');
    return password?.value == confirmPassword?.value ? null : { 'notSame': true };
  }

  onSubmit() {
    if (this.form.valid) {
      this.service.save(this.form.value).subscribe(
        result => this.onSuccess(),
        error => this.onError()
      );
    } else {
      this.formUtils.validateAllFormFields(this.form);
    }

    /*
    if (this.form.get('password')?.value != this.form.get('confirmPassword')?.value) {
      alert("Senhas diferentes")
    }
    */
  }

  onCancel() {
    this.location.back();
  }

  private onSuccess() {
    this.snackBar.open('Novo usuário salvo com sucesso', '',
      { duration : 5000, });
    //this.onCancel();
  }

  private onError() {
    this.snackBar.open('Erro ao salvar novo usuário', '',
      { duration : 5000, });
    //this.onCancel();
  }
}
