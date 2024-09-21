import { Component } from '@angular/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';

@Component({
  selector: 'app-data-binding-component',
  standalone: true,
  imports: [MatFormField, MatLabel],
  templateUrl: './data-binding-component.component.html',
  styleUrl: './data-binding-component.component.scss'
})

export class DataBindingComponentComponent {
  userName: string = '';

  OnInput(event: any) {
    this.userName = event.target.value;
  }
}
