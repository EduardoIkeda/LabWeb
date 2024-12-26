import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable } from 'rxjs';
import { HealthCenter } from '../model/health-center';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class HealthCenterService {
  private readonly API = '/api/health-centers';

  constructor(
    private readonly httpClient: HttpClient,
    private readonly snackBar: MatSnackBar,
    private readonly location: Location
  ) {}

  list(): Observable<HealthCenter[]> {
    return this.httpClient.get<HealthCenter[]>(this.API).pipe(
      first()
      //delay(10000),
      //tap(postos => console.log(postos))
    );
  }

  listBySpecialty(specialtyId: string): Observable<HealthCenter[]> {
    return this.httpClient.get<HealthCenter[]>(`${this.API}/by-specialty/${specialtyId}`).pipe(
      first()
      //delay(10000),
      //tap(postos => console.log(postos))
    );
  }

  loadById(id: string): Observable<HealthCenter> {
    return this.httpClient.get<HealthCenter>(`${this.API}/${id}`);
  }

  save(record: Partial<HealthCenter>) {
    if (record.id) {
      return this.update(record).subscribe({
        next: (response) => {
          console.log(response);
          this.snackBar.open(
            'Posto de saúde atualizado com sucesso!',
            'Close',
            {
              duration: 3000,
            }
          );
          this.location.back();
        },
        error: (err) => {
          console.error(err);
          this.snackBar.open('Falha ao atualizar o posto de saude.', 'Close', {
            duration: 3000,
          });
        },
      });
    } else {
      return this.create(record).subscribe({
        next: (response) => {
          console.log(response);
          this.snackBar.open('Posto de saúde criado com sucesso!', 'Close', {
            duration: 3000,
          });
          this.location.back();
        },
        error: (err) => {
          console.error(err);
          this.snackBar.open('Falha ao cadastrar o posto de saude.', 'Close', {
            duration: 3000,
          });
        },
      });
    }
  }

  private create(record: Partial<HealthCenter>): Observable<HealthCenter> {
    const body = {
      name: record.name,
      address: record.address,
      openingHour: record.openingHour,
      closingHour: record.closingHour,
      specialtyIds: record.specialties?.map(speciality => speciality.id)
    };

    return this.httpClient.post<HealthCenter>(this.API, body);
  }

  private update(record: Partial<HealthCenter>) {
    const body = {
      id: record.id,
      name: record.name,
      address: record.address,
      openingHour: record.openingHour,
      closingHour: record.closingHour,
      specialtyIds: record.specialties?.map(speciality => speciality.id)
    };

    return this.httpClient.put<HealthCenter>(`${this.API}/${record.id}`, body);
  }

  addDoctors(record: Partial<HealthCenter>): Observable<HealthCenter> {
    const body = {
      name: record.name,
      address: record.address,
      openingHour: record.openingHour,
      closingHour: record.closingHour,
      specialtyIds: record.specialties?.map(speciality => speciality.id),
      doctorIds: record.doctors?.map(doctor => doctor.id)
    };

    return this.httpClient.patch<HealthCenter>(`${this.API}/${record.id}`, body);
  }

  remove(id: string | null): Observable<any> {
    return this.httpClient.delete(`${this.API}/${id}`);
  }
}
