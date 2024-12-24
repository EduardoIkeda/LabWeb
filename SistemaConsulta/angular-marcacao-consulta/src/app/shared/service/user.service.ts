import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../auth/model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly API = 'assets/user.json';

  constructor(private readonly http: HttpClient) {}

  get() {
    return this.http.get<User>(this.API);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(`${this.API}`, user);
  }
}
