import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DadosService {
  constructor() {}

  getData() {
    return [
      { nome: 'Eduardo', idade: 24 },
      { nome: 'Adriana', idade: 22 },
    ];
  }
}
