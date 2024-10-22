import { CommonModule, Location} from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UsersService } from '../services/users.service';
import { FormUtilsService } from '../../shared/form/form-utils.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    CommonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
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
      password: ['',
        [Validators.required,
        Validators.minLength(8)]]
    });
  }

  ngOnInit(): void { }

  onSubmit() {
    if (this.form.valid) {
      this.onSuccess()
    } else {
      this.formUtils.validateAllFormFields(this.form);
    }
  }

  onCancel() {
    this.location.back();
  }

  private onSuccess() {
    this.snackBar.open('Login efetuado com sucesso', '',
      { duration : 5000, });
    //this.onCancel();
  }

  private onError() {
    this.snackBar.open('Erro ao efetuar login', '',
      { duration : 5000, });
    //this.onCancel();
  }
}
