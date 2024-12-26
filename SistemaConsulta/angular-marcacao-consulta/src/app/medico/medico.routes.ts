import { Routes } from '@angular/router';
import { MedicoComponent } from './medico.component';
import { MedicoConsultaComponent } from './medico-consulta/medico-consulta.component';
import { AuthGuard } from '../auth/guards/auth-guard.service';

export const MEDICO_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'perfil'
  },
  {
    path: 'perfil',
    component: MedicoComponent,
    canActivate: [AuthGuard], data: { roles: ['doctor', 'admin'] }
  },
  {
    path: 'perfil/:id',
    component: MedicoComponent,
    canActivate: [AuthGuard], data: { roles: ['doctor', 'admin'] }
  },
  {
    path: 'consultas/:id',
    component: MedicoConsultaComponent,
    canActivate: [AuthGuard], data: { roles: ['doctor', 'admin'] }
  }
];
