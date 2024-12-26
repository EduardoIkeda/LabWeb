import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../auth/model/user';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly API = 'api/users';

  constructor(private readonly http: HttpClient) {}

  get(userId: string) {
    return this.http.get<User>(`${this.API}/${userId}`);
  }

  update(record: Partial<User>): Observable<User> {
    return this.http.patch<User>(`${this.API}/${record.id}`, record);
  }

  docgetById(userId: string): Observable<User | undefined> {
    return this.http.get<User>(this.API).pipe(
      map(user => user.id === userId ? user : undefined)
    );
  }
  getById(userId: string): Observable<User> {
    return this.http.get<User>(`${this.API}/${userId}`);
  }

}
