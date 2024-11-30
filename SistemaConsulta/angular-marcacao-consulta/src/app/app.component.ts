import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { Router, RouterOutlet } from '@angular/router';

import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { TopbarComponent } from './shared/topbar/topbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    SidebarComponent,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    TopbarComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'angular-marcacao-consulta';
  showFiller = false;

  constructor(public router: Router) {}

  isRouteAuth() {
    return this.router.url.includes('auth');
  }

}
