import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class UserServiceService {
  usersHttp: { name: string; username: string }[] = [];

  constructor(private http: HttpClient) { }

  getUsers() {

    // Complete o código para fazer uma requisição GET
    // para a URL 'https://jsonplaceholder.typicode.com/users'
    // e retornar o resultado.
    this.http.get<{ name: string; username: string }[]>('https://jsonplaceholder.typicode.com/users')
      .subscribe(users => {
        this.usersHttp = users;
      });
    return this.usersHttp;

  }
}
