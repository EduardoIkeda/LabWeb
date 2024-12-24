import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable, of, tap, throwError } from 'rxjs';

import { AuthResponse } from '../model/auth-response';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private readonly API = 'api/users';
  private readonly noAuthHeader = new HttpHeaders({ 'No-Auth': 'True' });

  constructor(private httpClient: HttpClient) { }

  // Testando se a autenticação com Token JWT está funcionando
  teste() {
    return this.httpClient.get<{ message: string }>(`${this.API}/teste`);
  }

  login(record: Partial<User>) {
    return this.httpClient.post<AuthResponse>(`${this.API}/login`, record, { headers: this.noAuthHeader }).pipe(
      tap((value) => {
        localStorage.setItem("user_id", value.id)
        localStorage.setItem("name", value.name)
        localStorage.setItem("role", value.role)
        localStorage.setItem("auth-token", value.token)
      })
    )
  }

  signup(record: Partial<User>) {
    return this.httpClient.post<AuthResponse>(`${this.API}/register`, record, { headers: this.noAuthHeader }).pipe(
      tap((value) => {
        localStorage.setItem("user_id", record.id!)
        localStorage.setItem("name", value.name)
        localStorage.setItem("role", value.role)
        localStorage.setItem("auth-token", value.token)
      })
    )
  }

  list() {
    return this.httpClient.get<User[]>(this.API)
      .pipe(
        first()
        //delay(10000),
        //tap(users => console.log(users))
      );
  }

  loadById(id: string) {
    return this.httpClient.get<User>(`${this.API}/${id}`);
  }

  private create(record: Partial<User>) {
    return this.httpClient.post<User>(this.API, record);
  }

  private update(record: Partial<User>) {
    return this.httpClient.put<User>(`${this.API}/${record.id}`, record);
  }

  remove(id: string) {
    return this.httpClient.delete(`${this.API}/${id}`);
  }
}
