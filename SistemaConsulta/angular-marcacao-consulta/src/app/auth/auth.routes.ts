import { Routes } from '@angular/router';

import { AuthGuard } from './guards/auth-guard.service';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { TesteComponent } from './teste/teste.component';

export const AUTH_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: "teste", component: TesteComponent, canActivate: [AuthGuard] }
];
