import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})

export class ProfileComponent {
  @Input() name: string = 'John Doe';
  @Input() susNumber: string = '1234567890';
  @Input() status: string = 'Active';
  @Input() avatarUrl: string = 'https://material.angular.io/assets/img/examples/shiba1.jpg';

  @Output() profileClicked = new EventEmitter<void>();

  onProfileClick() {
    this.profileClicked.emit();
  }
}

