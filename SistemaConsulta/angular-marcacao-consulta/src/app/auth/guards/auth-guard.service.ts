import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, @Inject(PLATFORM_ID) private platformId: Object) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (isPlatformBrowser(this.platformId)) {
      // console.log("Execução no navegador")
      const authToken = localStorage.getItem('auth-token');

      if (authToken) {
        return true;
      } else {
        this.router.navigate(['/login']);
        return false;
      }
    }
    // console.log("Execução no servidor")
    return false;
  }
}
