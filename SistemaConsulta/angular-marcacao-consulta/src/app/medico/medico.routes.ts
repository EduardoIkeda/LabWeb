import { Routes } from '@angular/router';

import { MedicoConsultaComponent } from './medico-consulta/medico-consulta.component';
import { MedicoComponent } from './medico.component';

export const MEDICO_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'perfil'
  },
  {
    path: 'perfil',
    component: MedicoComponent
  },
  {
    path: 'perfil/:id',
    component: MedicoComponent
  },
  {
    path: 'consultas/:id',
    component: MedicoConsultaComponent
  }
];
