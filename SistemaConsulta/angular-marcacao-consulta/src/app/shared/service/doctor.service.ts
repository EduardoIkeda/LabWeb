import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { Doctor } from '../model/doctor';
import { first, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Especialidade } from '../model/especialidade';

@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  private readonly API = '/api/doctors';
  private readonly CAPI = 'assets/medico.json';
  private readonly specialtiesApi = 'assets/especialidades.json';


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

  getDoctorById(id: string): Observable<Doctor> {
    console.log('Buscando dados do médico com ID:', id);
    return this.httpClient.get<Doctor[]>(this.CAPI).pipe(
      first(),
      map((doctors: Doctor[]) => {
        const doctor = doctors.find(d => d.id === id);
        if (!doctor) {
          throw new Error('Médico não encontrado');
        }
        console.log('Médico encontrado:', doctor);
        return doctor;
      })
    );
  }
  getSpecialties(): Observable<Especialidade[]> {
    return this.httpClient.get<Especialidade[]>(this.specialtiesApi).pipe(first());
  }

  listByHealthCenter(healthCenterId: string): Observable<Doctor[]> {
      return this.httpClient.get<Doctor[]>(`${this.API}/by-health-center/${healthCenterId}`).pipe(
        first()
        //delay(10000),
        //tap(postos => console.log(postos))
      );
    }
}
