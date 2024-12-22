import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';

export function AuthInterceptor(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
  const router = inject(Router);

  if (req.headers.get('No-Auth') === 'True') {
    return next(req);
  }

  const authToken = localStorage.getItem('auth-token');

  if (authToken) {
    //console.log("Passou pelo authToken")
    const clonedRequest = req.clone({ setHeaders: { Authorization: `Bearer ${authToken}` } });
    return next(clonedRequest);
  }

  router.navigate(['/login']);
  return throwError(() => new Error('Token de autenticação não encontrado.'));
}
