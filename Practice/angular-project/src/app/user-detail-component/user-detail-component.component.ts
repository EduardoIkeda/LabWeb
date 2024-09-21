import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-detail-component',
  standalone: true,
  imports: [],
  templateUrl: './user-detail-component.component.html',
  styleUrl: './user-detail-component.component.scss'
})
export class UserDetailComponentComponent implements OnInit {

  userId!: string;

  constructor(private route: ActivatedRoute) { }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = params['id'];
    });
    // Implemente o código para capturar o ID do usuário da rota
    }
  }
