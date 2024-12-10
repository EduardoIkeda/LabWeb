import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Especialidade } from '../../model/especialidade';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import { EspecialidadeItemComponent } from "./especialidade-item/especialidade-item.component";
import { EspecialidadesService } from './services/especialidades.service';

@Component({
  selector: 'app-especialidades',
  standalone: true,
  imports: [EspecialidadeItemComponent, CommonModule, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './especialidades.component.html',
  styleUrl: './especialidades.component.scss'
})
export class EspecialidadesComponent implements OnInit{
  @Output() selectSpeciality = new EventEmitter<Especialidade>();
  displayedSpeciality: Especialidade[] = [];
  query!: string;
  especialidades: Especialidade[] = [];

  constructor(private readonly especialidadesService: EspecialidadesService) {
  }

  ngOnInit(): void {
    this.loadEspecialidades();
  }

  loadEspecialidades() {
    this.especialidadesService.list().subscribe({
      next: (especialidades) => {
        this.especialidades = especialidades.map((especialidade) => ({ ...especialidade }));
        // Após carregar as especialidades, ordene e atribua a displayedSpeciality
        this.displayedSpeciality = this.especialidades.sort((a, b) => a.name.localeCompare(b.name));
      },
      error: (error) => console.error('Error:', error),
    });
  }

  onChange(event: any){
    this.query = event.target.value.trim().toLowerCase();

    if(this.query != ""){
      this.displayedSpeciality = [];
      for(let speciality of this.especialidades){
        if(speciality.name.toLowerCase().includes(this.query)){
          this.displayedSpeciality.push(speciality);
        }
      }
    }else{
      this.displayedSpeciality = this.especialidades.sort((a, b) => a.name.localeCompare(b.name));
    }
  }

  onSelectSpeciality(speciality: Especialidade){
    this.selectSpeciality.emit(speciality);
  }
}
