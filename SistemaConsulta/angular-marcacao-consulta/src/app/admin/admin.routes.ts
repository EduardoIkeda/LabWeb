import { Routes } from '@angular/router';
import { DashboardComponent } from './container/dashboard/dashboard.component';
import { PostoSaudeComponent } from './container/posto-saude/posto-saude.component';
import { PostoSaudeFormComponent } from './container/posto-saude-form/posto-saude-form.component';
import { MedicoFormComponent } from './container/medico-form/medico-form.component';
import { PostoSaudeResolver } from './guards/posto-saude.resolver';

export const ADMIN_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'posto-saude', component: PostoSaudeComponent },
  {
    path: 'posto-saude/new',
    component: PostoSaudeFormComponent,
    resolve: { postoSaude: PostoSaudeResolver },
  },
  {
    path: 'posto-saude/edit/:id',
    component: PostoSaudeFormComponent,
    resolve: { postoSaude: PostoSaudeResolver },
  },
  {
    path: 'posto-saude/editmedico/:id',
    component: MedicoFormComponent,
    resolve: { postoSaude: PostoSaudeResolver },
  },

  // { path: 'create-especialidade', component: MarcarConsultaComponent },
];
