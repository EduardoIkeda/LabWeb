import { CommonModule, Location } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';

import { User } from '../auth/model/user';
import { UserService } from '../shared/service/user.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatInputModule,
    CommonModule,
    MatCardModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule // Aqui está a adição do ReactiveFormsModule
  ],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {
  isEditing = false;
  formulario: FormGroup;
  user: User = {
    id: '',
    susCardNumber: '',
    name: '',
    cpf: '',
    phone: '',
    email: '',
    password: '',
    avatarUrl: '',
    status: '',
    role: ''
  };

  @Output() profileClicked = new EventEmitter<void>();

  constructor(
    private readonly userService: UserService,
    private readonly formBuilder: FormBuilder,
    private readonly location: Location
  ) {
    this.formulario = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
      phone: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(13), Validators.pattern('^[0-9]*$')]],
    });
  }

  ngOnInit(): void {
    const userId = localStorage.getItem("user_id");
    if (!userId) {
      throw new Error("Usuário não encontrado no localStorage.");
    }

    this.userService.get(userId).subscribe((user) => {
      this.user = user;
      this.formulario.patchValue({
        name: user.name,
        email: user.email,
        phone: user.phone
      });
    });
  }

  onProfileClick() {
    this.profileClicked.emit();
  }

  toggleEditMode() {
    if (this.isEditing) {
      this.onSubmit();
    }

    this.isEditing = !this.isEditing;
  }

  onSubmit() {
    if (this.formulario.valid) {
      const updatedUser = { ...this.user, ...this.formulario.value };
      this.userService.update(updatedUser).subscribe(() => {
        this.user = updatedUser;

        this.formulario.patchValue({
          name: this.user.name,
          email: this.user.email,
          phone: this.user.phone
        });
      });
    } else {
      alert('Form is invalid');
    }
  }

  onBack() {
    this.location.back();
  }
}
