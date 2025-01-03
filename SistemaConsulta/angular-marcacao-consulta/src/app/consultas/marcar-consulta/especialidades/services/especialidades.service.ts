import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Especialidade } from '../../../../shared/model/especialidade';

@Injectable({
  providedIn: 'root',
})
export class EspecialidadesService {
  //private readonly API = 'assets/especialidades.json';
  private readonly API = '/api/specialties';

  constructor(private readonly http: HttpClient) {}

  list() {
    return this.http.get<Especialidade[]>(this.API);
  }
}
