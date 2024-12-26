import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { map, of } from 'rxjs';

import { Consulta } from '../../shared/model/consulta';
import { ConsultaPorData } from '../model/consulta_por_data';

@Injectable({
  providedIn: 'root',
})
export class ConsultasService {
  private readonly API = '/api/appointments';
  private readonly API_test = 'assets/consultas.json';

  constructor(private readonly http: HttpClient) { }

  list() {
    return this.http.get<Consulta[]>('assets/consultas.json');
  }

  listByUser() {
    if (isPlatformBrowser(inject(PLATFORM_ID))) {
      const userId = localStorage.getItem("user_id");

      if (!userId) {
        throw new Error("Usuário não encontrado no localStorage.");
      }

      return this.http.get<Consulta[]>(`${this.API}/by-user/${userId}`);
    } else {
      console.log("Execução no servidor. Acesso ao localStorage não é permitido.");
      return of([]);
    }
  }

  listByDoctor(doctorName: string) {
    // Aqui estamos assumindo que o nome do médico está no JSON ou API
    return this.http.get<Consulta[]>(this.API_test).pipe(
      map((consultas) => consultas.filter((consulta) => consulta.doctorName === doctorName))
    );
  }

  listGroup(specialty_id: string, posto_id: string) {
    const params = new HttpParams()
      .set('healthCenterId', posto_id)
      .set('specialtyId', specialty_id);

    return this.http.get<ConsultaPorData[]>(`${this.API}/group`, { params });
  }

  loadById(id: string) {
    return this.http.get<Consulta>(`${this.API}/${id}`);
  }

  marcarConsulta(record: Partial<Consulta>) {
    return this.http.patch<Consulta>(`${this.API}/schedule/${record.id}`, record);
  }

  cancelarConsulta(record: Partial<Consulta>) {
    return this.http.patch<Consulta>(`${this.API}/cancel/${record.id}`, {});
  }
}
