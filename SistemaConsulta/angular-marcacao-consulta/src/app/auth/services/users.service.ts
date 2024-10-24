import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, of } from 'rxjs';

import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private readonly API = 'api/users';

  constructor(private httpClient: HttpClient) { }

  list(){
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

  save(record: Partial<User>){
    return of(true);
    /*
    if (record.id){
      return this.update(record);
    } else {
      return this.create(record);
    }
    */
  }

  private create(record: Partial<User>){
    return this.httpClient.post<User>(this.API, record);
  }

  private update(record: Partial<User>){
    return this.httpClient.put<User>(`${this.API}/${record.id}`, record);
  }

  remove(id: string){
    return this.httpClient.delete(`${this.API}/${id}`);
  }
}
