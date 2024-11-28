import { Routes } from '@angular/router';
import { ConsultasComponent } from './container/consultas/consultas.component';

export const CONSULTAS_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'list' },
  { path: 'list', component: ConsultasComponent },
];
