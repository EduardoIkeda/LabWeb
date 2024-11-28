import { Routes } from '@angular/router';
import { ConsultasComponent } from './consultas/consultas.component';

export const CONSULTAS_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'list' },
  { path: 'list', component: ConsultasComponent },
];
