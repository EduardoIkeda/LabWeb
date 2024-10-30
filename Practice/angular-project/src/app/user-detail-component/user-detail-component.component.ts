import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-detail-component',
  standalone: true,
  imports: [],
  templateUrl: './user-detail-component.component.html',
  styleUrl: './user-detail-component.component.scss',
})
export class UserDetailComponentComponent implements OnInit {
  userId!: string;

  constructor(private readonly route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.userId = params['id'];
    });
  }
}
