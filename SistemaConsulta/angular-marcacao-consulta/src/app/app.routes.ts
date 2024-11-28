import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'auth' },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.routes').then((m) => m.AUTH_ROUTES),
  },
  {
    path: 'consultas',
    loadChildren: () =>
      import('./consultas/consultas.routes').then((m) => m.CONSULTAS_ROUTES),
  },
];
