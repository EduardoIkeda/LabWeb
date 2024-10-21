import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'auth', },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
];

