import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { HealthCenter } from '../../shared/model/health-center';
import { PostoSaudeService } from '../../shared/service/health-center.service';

@Injectable({
  providedIn: 'root',
})
export class PostoSaudeResolver implements Resolve<HealthCenter> {
  constructor(private readonly service: PostoSaudeService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<HealthCenter> {
    if (route.params && route.params['id']) {
      return this.service.loadById(route.params['id']);
    }
    return of({
      id: '',
      name: '',
      address: '',
      // especialidades: [],
      openingHour: '',
      closingHour: '',
    });
  }
}
