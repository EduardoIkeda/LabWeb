import { Component, OnDestroy, OnInit } from '@angular/core';
import { EspecialidadesComponent } from "./especialidades/especialidades.component";
import { Consulta } from '../model/consulta';
import { Especialidade } from '../model/especialidade';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { PostosComponent } from './postos/postos.component';
import { Posto } from '../model/posto';

@Component({
  selector: 'app-marcar-consulta',
  standalone: true,
  imports: [EspecialidadesComponent, MatIconModule, MatButtonModule, MatCardModule, PostosComponent],
  templateUrl: './marcar-consulta.component.html',
  styleUrl: './marcar-consulta.component.scss'
})
export class MarcarConsultaComponent implements OnInit, OnDestroy{
  consulta!: Consulta;
  speciality!: Especialidade;
  posto!: Posto;

  onSelectSpeciality(speciality: Especialidade){
    this.speciality = speciality;
    console.log(this.speciality.name);
  }

  onSelectPosto(posto: Posto){
    this.posto = posto;
    console.log(this.posto.name);
  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {

  }
}
