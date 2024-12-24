import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';

import { Consulta } from '../../shared/model/consulta';
import { isPlatformBrowser } from '@angular/common';
import { throwError } from 'rxjs';
import { ConsultaPorData } from '../model/consulta_por_data';

@Injectable({
  providedIn: 'root',
})
export class ConsultasService {
  private readonly API = '/api/appointments';
  //private readonly API = 'assets/consultas.json';

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
      return throwError(() => new Error('Execução no servidor. Acesso ao localStorage não é permitido.'));
    }
  }

  listGroup(specialty_id: string, posto_id: string) {
    const params = new HttpParams()
      .set('healthCenterId', posto_id)
      .set('specialtyId', specialty_id);

    return this.http.get<ConsultaPorData[]>(`${this.API}/group`, { params });
    //return this.http.get<ConsultaPorData[]>('assets/lista_consultas_especialidade_posto.json', { params: { speciality_id, posto_id } });
  }

  marcarConsulta(record: Partial<Consulta>) {
    return this.http.put<Consulta>(`${this.API}/schedule/${record.id}`, record);
  }
}
