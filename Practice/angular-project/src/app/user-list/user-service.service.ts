import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  usersHttp: { name: string; username: string }[] = [];
  api = 'https://jsonplaceholder.typicode.com/users';

  constructor(private http: HttpClient) {}

  getUsers() {
    this.http
      .get<{ name: string; username: string }[]>(this.api)
      .subscribe((users) => {
        this.usersHttp = users;
      });
    return this.usersHttp;
  }
}
