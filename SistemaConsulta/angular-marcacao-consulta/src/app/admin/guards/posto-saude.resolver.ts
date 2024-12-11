import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  ResolveFn,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { PostoSaude } from '../../shared/model/posto-saude';
import { PostoSaudeService } from '../service/posto-saude.service';

@Injectable({
  providedIn: 'root',
})
export class PostoSaudeResolver {
  constructor(private readonly service: PostoSaudeService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<PostoSaude> {
    if (route.params && route.params['id']) {
      return this.service.loadById(route.params['id']);
    }
    return of({ id: '', nome: '', endereco: '', especialidades: [], horarioAbertura: '', horarioFechamento: '' });
  }
}

export const postoSaudeResolver: ResolveFn<boolean> = (route, state) => {
  return true;
};
