import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { Doctor } from '../model/doctor';
import { first, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Posto } from '../model/posto';
@Injectable({
  providedIn: 'root',
})
export class PostoService {
  private readonly API = 'assets/postos.json';
  postos: any;

  constructor(private readonly http: HttpClient) {}

  list(): Observable<Posto[]> {
    return this.http.get<Posto[]>(this.API);
  }

  get() {
    return this.http.get<Posto>(this.API);
  }

  update(posto: Posto): Observable<Posto> {
    return this.http.put<Posto>(`${this.API}`, posto);
  }

  getPostoById(postoId: string): Posto | undefined {
    return this.postos.find((posto: { id: string }) => posto.id === postoId);
  }
}
