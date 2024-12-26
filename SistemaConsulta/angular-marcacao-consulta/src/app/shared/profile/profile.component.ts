import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

import { User } from '../../auth/model/user';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent {
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

  constructor(
    private readonly userService: UserService,
    private readonly router: Router
  ) {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      throw new Error("Usuário não encontrado no localStorage.");
    }

    this.userService.get(userId).subscribe((user) => {
      this.user = user;
    });
  }

  onProfileClick() {
    this.router.navigate(['/perfil']);
  }
}
