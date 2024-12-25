import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { Doctor } from '../model/doctor';
import { first, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  private readonly API = '/api/doctors';

  constructor(
    private readonly httpClient: HttpClient,
    private readonly snackBar: MatSnackBar,
    private readonly location: Location
  ) {}

  list(): Observable<Doctor[]> {
    return this.httpClient.get<Doctor[]>(this.API).pipe(
      first(),
      map(doctors => doctors.map(doctor => ({
      ...doctor,
      name: doctor.doctorName || 'placeholder'
      })))
    );
  }

  listByHealthCenter(healthCenterId: string): Observable<Doctor[]> {
      return this.httpClient.get<Doctor[]>(`${this.API}/by-health-center/${healthCenterId}`).pipe(
        first()
        //delay(10000),
        //tap(postos => console.log(postos))
      );
    }
}
