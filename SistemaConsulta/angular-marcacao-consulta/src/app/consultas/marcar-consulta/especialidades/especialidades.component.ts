import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Especialidade } from '../../model/especialidade';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import { EspecialidadeItemComponent } from "./especialidade-item/especialidade-item.component";

@Component({
  selector: 'app-especialidades',
  standalone: true,
  imports: [EspecialidadeItemComponent, CommonModule, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './especialidades.component.html',
  styleUrl: './especialidades.component.scss'
})
export class EspecialidadesComponent implements OnInit{
  @Output() selectSpeciality = new EventEmitter<Especialidade>();
  displayedSpeciality!: Especialidade[];
  query!: string;

  especialidades: Especialidade[] = [
    new Especialidade(
      '0',
      'Cardiologista',
      'Cuida do coração'
    ),
    new Especialidade(
      '1',
      'Oftamologista',
      'Cuida dos olhos'
    ),
    new Especialidade(
      '2',
      'Nutricionista',
      'Te deixa fit'
    ),
    new Especialidade(
      '3',
      'Clínico geral',
      'Cuidado de você'
    ),
  ];

  ngOnInit(): void {
    this.displayedSpeciality = this.especialidades.sort((a, b) => a.name.localeCompare(b.name));
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
