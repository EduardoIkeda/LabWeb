import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  constructor() { }

  getData() {
    return ['item1', 'item2', 'item3'];
  }
}
