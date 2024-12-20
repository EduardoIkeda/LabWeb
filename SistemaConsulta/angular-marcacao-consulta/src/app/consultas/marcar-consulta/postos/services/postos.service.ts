import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Posto } from '../../../../shared/model/posto';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PostosService {
  private readonly API = 'assets/postos.json';

  constructor(private readonly http: HttpClient) {}

  list() {
    return this.http.get<Posto[]>(this.API);
  }
}
