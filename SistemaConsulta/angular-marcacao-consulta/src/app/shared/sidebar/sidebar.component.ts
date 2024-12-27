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
  isDoctor: boolean = false;
  isAdmin: boolean = false;
  userId: null | string = '';

  constructor(private readonly router: Router) { }

  ngOnInit(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userRole = localStorage.getItem('userRole');
      this.userId = localStorage.getItem('userId');

      this.isDoctor = (userRole === 'doctor');
      this.isAdmin = (userRole === 'admin');
    }
  }

  goToPage(page: string) {
    console.log(page);
    this.router.navigate([page]);
  }

  logout() {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      localStorage.removeItem("userId");
      localStorage.removeItem("userName");
      localStorage.removeItem("userAvatarUrl");
      localStorage.removeItem("userRole");
      localStorage.removeItem("authToken");

      this.router.navigate(['/auth/login']);
    }
  }
}
