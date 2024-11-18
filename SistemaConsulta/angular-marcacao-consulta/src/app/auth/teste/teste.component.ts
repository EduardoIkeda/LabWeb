import { Component } from '@angular/core';
import { UsersService } from '../services/users.service';

@Component({
  selector: 'app-teste',
  standalone: true,
  imports: [],
  templateUrl: './teste.component.html',
  styleUrl: './teste.component.scss'
})
export class TesteComponent {
  message!: string;

  constructor(private userService: UsersService) { }

  ngOnInit() {
    this.teste();
  }

  teste() {
    this.userService.teste().subscribe({
      next: (result) => {
        console.log(result.message);
        this.message = result.message;
      },
      error: (err) => {
        console.error('Erro no subscribe:', err);
      }
    })
  }

}
