import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

import { User } from '../../auth/model/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent {
  user!: User;

  constructor(private readonly userService: UserService) {
    this.userService.get().subscribe((user) => {
      this.user = user;
    });
  }

  @Output() profileClicked = new EventEmitter<void>();

  get name(): string {
    return this.user.name || 'John Doe';
  }

  get susNumber(): string {
    return this.user.susCardNumber || '1234567890';
  }

  get status(): string {
    return this.user.status || 'Active';
  }

  get avatarUrl(): string {
    return (
      this.user.avatarUrl ||
      'https://material.angular.io/assets/img/examples/shiba1.jpg'
    );
  }

  onProfileClick() {
    this.profileClicked.emit();
  }
}
