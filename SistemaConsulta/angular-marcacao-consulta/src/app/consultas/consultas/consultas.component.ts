import { Component } from '@angular/core';
import { ProfileComponent } from '../../shared/profile/profile.component';

@Component({
  selector: 'app-consultas',
  standalone: true,
  imports: [ProfileComponent],
  templateUrl: './consultas.component.html',
  styleUrl: './consultas.component.scss'
})
export class ConsultasComponent {

}
