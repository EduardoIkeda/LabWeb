import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';  // Importando ReactiveFormsModule aqui
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
  user!: User;

  @Output() profileClicked = new EventEmitter<void>();

  constructor(
    private readonly userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.formulario = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
      phone: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(12), Validators.pattern('^[0-9]*$')]],
    });
  }

  ngOnInit(): void {
    this.userService.get().subscribe((user) => {
      this.user = user;
      this.formulario.patchValue({
        name: user.name,
        email: user.email,
        phone: user.phone
      });
    });
  }

  toggleEditMode() {
    this.isEditing = !this.isEditing;
    if (!this.isEditing) {
      this.formulario.patchValue({
        name: this.user.name,
        email: this.user.email,
        phone: this.user.phone
      });
    }
  }

  get name(): string {
    return this.user?.name || 'John Doe';
  }
  get email(): string {
    return this.user?.email || 'John Doe';
  }

  get susNumber(): string {
    return this.user?.susCardNumber || '1234567890';
  }

  get status(): string {
    return this.user?.status || 'Active';
  }
  get cpf(): string {
    return this.user?.cpf || '000000000';
  }

  get avatarUrl(): string {
    return (
      this.user?.avatarUrl ||
      'https://material.angular.io/assets/img/examples/shiba1.jpg'
    );
  }

  onProfileClick() {
    this.profileClicked.emit();
  }

  onSubmit() {
    if (this.formulario.valid) {
      const updatedUser = { ...this.user, ...this.formulario.value };
      this.userService.update(updatedUser).subscribe(() => {
        this.user = updatedUser;
        this.toggleEditMode();
      });
    } else {
      alert('Form is invalid');
    }
  }
}
