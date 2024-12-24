import { Routes } from '@angular/router';
import { PerfilComponent } from './perfil.component';


export const PERFIL_ROUTES: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'perfil' },
  { path: 'perfil', component: PerfilComponent },

];
