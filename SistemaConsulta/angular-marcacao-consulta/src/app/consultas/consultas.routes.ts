import { Routes } from '@angular/router';

import { ConsultasComponent } from './container/consultas/consultas.component';
import { MarcarConsultaComponent } from './marcar-consulta/marcar-consulta.component';
import { RemarcarConsultaComponent } from './remarcar-consulta/remarcar-consulta.component';

export const CONSULTAS_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'list'
  },
  {
    path: 'list',
    component: ConsultasComponent
  },
  {
    path: 'create',
    component: MarcarConsultaComponent
  },
  {
    path: 'reschedule/:id',
    component: RemarcarConsultaComponent
  }
];
