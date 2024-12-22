import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Posto } from '../../../shared/model/posto';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import { PostosService } from './services/postos.service';
import { PostoItemComponent } from './posto-item/posto-item.component';
import { Especialidade } from '../../../shared/model/especialidade';

@Component({
  selector: 'app-postos',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatIconModule, PostoItemComponent],
  templateUrl: './postos.component.html',
  styleUrl: './postos.component.scss'
})
export class PostosComponent implements OnInit{
  @Output() selectPosto = new EventEmitter<Posto>();
  @Input() speciality!: Especialidade | null;
  postos: Posto[] = [];
  displayedPostos: Posto[] = [];
  query!: string;

  constructor(private readonly postosService: PostosService) {
  }

  ngOnInit(): void {
    this.loadPostos();
  }

  loadPostos() {
    this.postosService.list(this.speciality!.id).subscribe({
      next: (postos) => {
        this.postos = postos.map((posto) => ({ ...posto }));
        // Após carregar os postos, ordene e atribua a displayedPostos
        this.displayedPostos = this.postos.sort((a, b) => b.appointments.length - a.appointments.length);
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
      this.displayedPostos = this.postos.sort((a, b) => b.appointments.length - a.appointments.length);
    }
  }

  onSelectPosto(posto: Posto){
    this.selectPosto.emit(posto);
  }
}
