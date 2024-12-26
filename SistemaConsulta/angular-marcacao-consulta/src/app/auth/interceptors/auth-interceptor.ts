import { isPlatformBrowser } from '@angular/common';
import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';

export function AuthInterceptor(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
  const router = inject(Router);
  const ignoredUrls = ['/assets/', '/public-endpoint'];

  if (req.headers.get('No-Auth') === 'True' || ignoredUrls.some(url => req.url.includes(url))) {
    console.log("Requisição ignorada pelo interceptor");
    return next(req);
  }

  if (isPlatformBrowser(inject(PLATFORM_ID))) {
    // console.log("Execução no navegador")
    const authToken = localStorage.getItem('auth-token');

    if (authToken) {
      const clonedRequest = req.clone({ setHeaders: { Authorization: `Bearer ${authToken}` } });
      return next(clonedRequest);
    }
  } else {
    console.log("Execução no servidor. Acesso ao localStorage não é permitido.");
    return next(req);
  }

  setTimeout(() => router.navigate(['/login']), 0);
  return throwError(() => new Error('Token de autenticação não encontrado.'));
}
