import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, @Inject(PLATFORM_ID) private platformId: Object) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (isPlatformBrowser(this.platformId)) {
      // console.log("Execução no navegador")
      const authToken = localStorage.getItem('auth-token');

      if (authToken) {
        // console.log("Passou pelo authToken")
        const expectedRole = route.data['role'] as string;

        if (expectedRole) {
          // console.log("Passou pelo expectedRole")
          const userRole = localStorage.getItem('role');

          if (userRole) {
            // console.log("Passou pelo userRole")

            if (userRole === expectedRole) {
              // console.log("Passou pela comparação")
              return true;
            }
          }
          return false;
        }
        return true;
      }
      this.router.navigate(['/login']);
      return false;
    }
    // console.log("Execução no servidor")
    return false;
  }
}
