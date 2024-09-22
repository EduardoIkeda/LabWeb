import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormField } from '@angular/material/form-field';
import { MatLabel } from '@angular/material/form-field';
import { AsyncPipe, CommonModule, NgFor } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { UserServiceService } from './user-service.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports:
    [
      MatInputModule, MatButtonModule, MatIconModule, MatFormField, MatLabel,
      CommonModule, AsyncPipe, RouterOutlet, NgFor, HttpClientModule
    ],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})

export class UserListComponent implements OnInit {
  users = [
    { name: 'John', age: 25 },
    { name: 'Doe', age: 30 },
    { name: 'Smith', age: 35 }
  ];
  usersHttp: { name: string; username: string }[] = [];
  message: string = 'Clique no botão';

  constructor(private userService: UserServiceService) { }

  ngOnInit() {
    this.usersHttp = this.userService.getUsers();
  }
}
