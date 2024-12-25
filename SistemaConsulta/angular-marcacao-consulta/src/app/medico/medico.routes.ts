import { Routes } from '@angular/router';
import { MedicoComponent } from './medico.component';
import { MedicoConsultaComponent } from './medico-consulta/medico-consulta.component';

export const MEDICO_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'perfil' },
  { path: 'perfil', component: MedicoComponent },
  { path: 'perfil/:id', component: MedicoComponent },
  { path: 'consultas/:id', component: MedicoConsultaComponent}
];
