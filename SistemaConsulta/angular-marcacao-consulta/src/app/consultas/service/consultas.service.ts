import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Consulta } from '../../shared/model/consulta';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ConsultasService {
  private readonly API = 'assets/consultas.json';

  constructor(private readonly http: HttpClient) {}

  list() {
    return this.http.get<Consulta[]>(this.API);
  }

  marcarConsulta(record: Partial<Consulta>){
    return this.http.put<Consulta>(`${this.API}/${record.id}`, record);
  }
}
