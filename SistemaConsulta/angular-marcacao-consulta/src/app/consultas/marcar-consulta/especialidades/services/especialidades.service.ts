import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Especialidade } from '../../../model/especialidade';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EspecialidadesService {
  private readonly API = 'assets/especialidades.json';

  constructor(private readonly http: HttpClient) {}

  list() {
    return this.http.get<Especialidade[]>(this.API);
  }
}
