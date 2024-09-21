import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-toggle-component',
  standalone: true,
  imports: [MatIcon, MatButtonModule, CommonModule],
  templateUrl: './toggle-component.component.html',
  styleUrl: './toggle-component.component.scss'
})
export class ToggleComponentComponent {
  showMessage: boolean = false;

  OnClickBoolean() {
    this.showMessage = !this.showMessage;
  }
}
