import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  isAdmin: boolean = false;

  constructor(private readonly router: Router) { }

  ngOnInit(): void {
    // this.isAdmin = localStorage.getItem('role') === 'admin';
    this.isAdmin = true; // TODO Remover isso depois
  }

  goToPage(page: string) {
    this.router.navigate([page]);
  }

  logout() {
    localStorage.removeItem("user_id");
    localStorage.removeItem("name");
    localStorage.removeItem("role");
    localStorage.removeItem("auth-token");

    this.router.navigate(['/auth/login']);
  }
}
