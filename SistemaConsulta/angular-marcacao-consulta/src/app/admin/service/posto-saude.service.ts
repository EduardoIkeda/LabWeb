import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first } from 'rxjs';
import { PostoSaude } from '../../shared/model/posto-saude';

@Injectable({
  providedIn: 'root',
})
export class PostoSaudeService {
  private readonly API = 'api/postosaude';

  constructor(private httpClient: HttpClient) {}

  list() {
    return this.httpClient.get<PostoSaude[]>(this.API).pipe(
      first()
      //delay(10000),
      //tap(postos => console.log(postos))
    );
  }

  loadById(id: string) {
    return this.httpClient.get<PostoSaude>(`${this.API}/${id}`);
  }

  save(record: Partial<PostoSaude>) {
    if (record.id) {
      return this.update(record);
    } else {
      return this.create(record);
    }
  }

  private create(record: Partial<PostoSaude>) {
    return this.httpClient.post<PostoSaude>(this.API, record);
  }

  private update(record: Partial<PostoSaude>) {
    return this.httpClient.put<PostoSaude>(`${this.API}/${record.id}`, record);
  }

  remove(id: string) {
    return this.httpClient.delete(`${this.API}/${id}`);
  }
}
