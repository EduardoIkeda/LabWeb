import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Consulta } from '../../shared/model/consulta';

@Injectable({
  providedIn: 'root',
})
export class ConsultasService {
  private readonly API = '/api/appointments';

  constructor(private readonly http: HttpClient) { }

  list() {
    return this.http.get<Consulta[]>('assets/consultas.json');
  }

  marcarConsulta(record: Partial<Consulta>) {
    return this.http.put<Consulta>(`${this.API}/schedule/${record.id}`, record);
  }
}
