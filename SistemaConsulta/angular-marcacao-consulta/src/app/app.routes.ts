import { Routes } from '@angular/router';
import { AuthGuard } from './auth/guards/auth-guard.service';
import { GuestGuard } from './auth/guards/guest-guard.service';

export const APP_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'consultas' },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.routes').then((m) => m.AUTH_ROUTES),
    canActivate: [GuestGuard]
  },
  {
    path: 'consultas',
    loadChildren: () =>
      import('./consultas/consultas.routes').then((m) => m.CONSULTAS_ROUTES),
    canActivate: [AuthGuard], data: { roles: ['citizen', 'doctor', 'admin'] }
  },
  {
    path: 'perfil',
    loadChildren: () =>
      import('./perfil/perfil.routes').then((m) => m.PERFIL_ROUTES),
    canActivate: [AuthGuard], data: { roles: ['citizen', 'doctor', 'admin'] }
  },
  {
    path: 'medico',
    loadChildren: () =>
      import('./medico/medico.routes').then((m) => m.MEDICO_ROUTES),
    canActivate: [AuthGuard], data: { roles: ['doctor'] }
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('./admin/admin.routes').then((m) => m.ADMIN_ROUTES),
    canActivate: [AuthGuard], data: { roles: ['admin'] }
  },
  {
    path: '**',
    redirectTo: 'auth',
    pathMatch: 'full'
  }
];
