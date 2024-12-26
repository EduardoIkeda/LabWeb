import { Routes } from '@angular/router';
import { DashboardComponent } from './container/dashboard/dashboard.component';
import { PostoSaudeComponent } from './container/health-center/health-center.component';
import { HealthCenterFormComponent } from './container/health-center-form/health-center-form.component';
import { MedicoFormComponent } from './container/medico-form/medico-form.component';
import { PostoSaudeResolver } from './guards/health-center.resolver';

export const ADMIN_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'posto-saude', component: PostoSaudeComponent },
  {
    path: 'posto-saude/new',
    component: HealthCenterFormComponent,
    resolve: { postoSaude: PostoSaudeResolver },
  },
  {
    path: 'posto-saude/edit/:id',
    component: HealthCenterFormComponent,
    resolve: { postoSaude: PostoSaudeResolver },
  },
  {
    path: 'posto-saude/editmedico/:id',
    component: MedicoFormComponent,
    resolve: { postoSaude: PostoSaudeResolver },
  },

  // { path: 'create-especialidade', component: MarcarConsultaComponent },
];
