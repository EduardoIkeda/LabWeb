import { Routes } from '@angular/router';
import { DashboardComponent } from './container/dashboard/dashboard.component';

export const ADMIN_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'dashboard', component: DashboardComponent },
  // { path: 'create-especialidade', component: MarcarConsultaComponent },
];
