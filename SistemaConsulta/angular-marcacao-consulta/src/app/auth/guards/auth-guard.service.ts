import { isPlatformBrowser, Location } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private readonly router: Router,
    private readonly location: Location,
    @Inject(PLATFORM_ID) private readonly platformId: Object
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (isPlatformBrowser(this.platformId)) {
      const authToken = localStorage.getItem('authToken');

      if (authToken) {
        console.log("Passou pelo authToken")
        const expectedRoles = route.data['roles'] as string[];

        if (expectedRoles && expectedRoles.length > 0) {
          const userRole = localStorage.getItem('userRole');

          if (userRole && expectedRoles.includes(userRole)) {
            console.log("Permissão total")
            return true;
          }

          this.router.navigate(['/consultas']);
          return false;
        }

        return true;
      }

      this.router.navigate(['auth/login']);
      return false;
    }
    return false;
  }
}
