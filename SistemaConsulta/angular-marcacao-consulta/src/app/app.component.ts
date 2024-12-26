import { Component, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDrawer, MatSidenavModule } from '@angular/material/sidenav';
import { NavigationEnd, NavigationStart, Router, RouterOutlet } from '@angular/router';

import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { TopbarComponent } from './shared/topbar/topbar.component';
import { filter } from 'rxjs';

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
export class AppComponent implements OnInit {
  title = 'angular-marcacao-consulta';
  showFiller = false;
  @ViewChild('drawer') drawer!: MatDrawer;

  constructor(private readonly router: Router) {}

  ngOnInit(): void {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        if (this.drawer) {
          this.drawer.close();
        }
      });
  }

  isRouteAuth() {
    return this.router.url.includes('auth');
  }

  isRouteProfile() {
    return this.router.url.includes('perfil');
  }

}
