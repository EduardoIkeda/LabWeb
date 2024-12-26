import { Routes } from '@angular/router';
import { MarcarConsultaComponent } from './marcar-consulta/marcar-consulta.component';
import { ConsultasComponent } from './container/consultas/consultas.component';
import { RemarcarConsultaComponent } from './remarcar-consulta/remarcar-consulta.component';
import { AuthGuard } from '../auth/guards/auth-guard.service';

export const CONSULTAS_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'list'
  },
  {
    path: 'list',
    component: ConsultasComponent,
    canActivate: [AuthGuard], data: { roles: ['citizen', 'doctor', 'admin'] }
  },
  {
    path: 'create',
    component: MarcarConsultaComponent,
    canActivate: [AuthGuard], data: { roles: ['citizen', 'doctor', 'admin'] }
  },
  {
    path: 'reschedule/:id',
    component: RemarcarConsultaComponent,
    canActivate: [AuthGuard], data: { roles: ['citizen', 'doctor', 'admin'] }
  }
];
