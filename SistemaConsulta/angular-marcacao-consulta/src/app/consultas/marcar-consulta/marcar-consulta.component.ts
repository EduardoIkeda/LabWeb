import { Component, OnDestroy, OnInit } from '@angular/core';
import { EspecialidadesComponent } from "./especialidades/especialidades.component";
import { Consulta } from '../model/consulta';
import { Especialidade } from '../model/especialidade';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-marcar-consulta',
  standalone: true,
  imports: [EspecialidadesComponent, MatIconModule, MatButtonModule, MatCardModule],
  templateUrl: './marcar-consulta.component.html',
  styleUrl: './marcar-consulta.component.scss'
})
export class MarcarConsultaComponent implements OnInit, OnDestroy{
  consulta!: Consulta;
  speciality!: Especialidade;

  onSelectSpeciality(speciality: Especialidade){
    this.speciality = speciality;
    console.log(this.speciality.name);
  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {

  }
}
