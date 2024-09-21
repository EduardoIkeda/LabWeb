import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';


@Component({
  selector: 'app-click-button-component',
  standalone: true,
  imports: [MatIcon, MatButtonModule],
  templateUrl: './click-button-component.component.html',
  styleUrl: './click-button-component.component.scss'
})
export class ClickButtonComponentComponent {
  message: string = 'Clique no botão';

  OnClick() {
    this.message = 'Botão clicado';
  }
}
