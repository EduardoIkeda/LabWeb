import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Posto } from '../../model/posto';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import { PostosService } from './services/postos.service';

@Component({
  selector: 'app-postos',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './postos.component.html',
  styleUrl: './postos.component.scss'
})
export class PostosComponent implements OnInit{
  postos: Posto[] = [];
  displayedPostos!: Posto[];
  query!: string;

  constructor(private readonly postosService: PostosService) {
  }

  ngOnInit(): void {
    this.loadPostos();
    this.displayedPostos = this.postos.sort((a, b) => b.appointments - a.appointments);
  }

  loadPostos() {
    this.postosService.list().subscribe({
      next: (postos) => {
        this.postos = postos.map((postos) => ({
          ...postos
        }));
      },
      error: (error) => console.error('Error:', error),
    });
  }

  onChange(event: any){
    this.query = event.target.value.trim().toLowerCase();

    if(this.query != ""){
      this.displayedPostos = [];
      for(let speciality of this.postos){
        if(speciality.name.toLowerCase().includes(this.query)){
          this.displayedPostos.push(speciality);
        }
      }
    }else{
      this.displayedPostos = this.postos.sort((a, b) => a.appointments - b.appointments);
    }
  }
}
