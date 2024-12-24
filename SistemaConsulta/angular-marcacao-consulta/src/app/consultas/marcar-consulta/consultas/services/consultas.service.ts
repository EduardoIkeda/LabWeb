import { ListaConsultas } from './../../../model/lista_consultas';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ConsultasService {
  private readonly API = 'assets/lista_consultas_especialidade_posto.json';

  constructor(private readonly http: HttpClient) {}

  list(speciality_id: string, posto_id: string) {
    return this.http.get<ListaConsultas>(this.API, {params: {speciality_id, posto_id}});
  }
}
