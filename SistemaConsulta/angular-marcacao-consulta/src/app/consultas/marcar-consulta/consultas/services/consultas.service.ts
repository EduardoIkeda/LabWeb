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

  list() {
    return this.http.get<ListaConsultas>(this.API);
  }
}
