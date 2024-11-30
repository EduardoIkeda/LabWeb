import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { ProfileComponent } from '../profile/profile.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [MatIconModule, MatButtonModule, ProfileComponent, CommonModule],
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss'],
})
export class TopbarComponent {
  @Input() drawer: any;
  @Input() isRouterAuth: boolean = false;
  constructor() {}
}
