import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Consulta } from '../../shared/model/consulta';

@Injectable({
  providedIn: 'root',
})
export class ConsultasService {
  private readonly API = '/api/appointments';

  constructor(private readonly http: HttpClient) {}

  list() {
    return this.http.get<Consulta[]>('assets/consultas.json');
  }

  create(record: Partial<Consulta>) {
    const recordd: Partial<Consulta> = {
      appointmentDateTime: record.appointmentDateTime,
      //appointmentStatus: "scheduled"
    };
    return this.http.post<Consulta>(this.API, recordd);
  }
}
