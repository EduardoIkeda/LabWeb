import { Routes } from '@angular/router';
import { PerfilComponent } from './perfil.component';
import { AuthGuard } from '../auth/guards/auth-guard.service';


export const PERFIL_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'perfil'
  },
  {
    path: 'perfil',
    component: PerfilComponent,
    canActivate: [AuthGuard], data: { roles: ['citizen', 'doctor', 'admin'] }
  },

];
