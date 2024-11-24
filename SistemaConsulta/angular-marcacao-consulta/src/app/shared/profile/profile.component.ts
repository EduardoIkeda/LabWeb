import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})

export class ProfileComponent {
  name: string = 'John Doe';
  susNumber: string = '1234567890';
  status: string = 'Active';
  avatarUrl: string = 'https://material.angular.io/assets/img/examples/shiba1.jpg';
}
