import { AppComponent } from './app.component';
import { Routes } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { ToggleComponentComponent } from './toggle-component/toggle-component.component';
import { FormComponentComponent } from './form-component/form-component.component';
import { ClickButtonComponentComponent } from './click-button-component/click-button-component.component';
import { DataBindingComponentComponent } from './data-binding-component/data-binding-component.component';
import { FilterComponentComponent } from './filter-component/filter-component.component';
import { DataComponent } from './data/data.component';
import { UserDetailComponentComponent } from './user-detail-component/user-detail-component.component';
import { ParentComponentComponent } from './parent-component/parent-component.component';
import { AtividadejuntaComponent } from './atividadejunta/atividadejunta.component';

export const routes: Routes =
  [
    { path: 'userlist', component: UserListComponent },
    // { path: '', redirectTo: '/userlist', pathMatch: 'full' },
    { path: 'toggle', component: ToggleComponentComponent },
    { path: 'form', component: FormComponentComponent },
    { path: 'clickbutton', component: ClickButtonComponentComponent},
    { path: 'databinding', component: DataBindingComponentComponent },
    { path: 'filter', component: FilterComponentComponent},
    { path: 'data', component: DataComponent},
    { path: 'userdetail', component: UserDetailComponentComponent},
    { path: 'userdetail/:id', component: UserDetailComponentComponent},
    { path: 'parent', component: ParentComponentComponent},
    { path: 'atividadejunta', component: AtividadejuntaComponent},
    { path: 'atividadejunta/:id', component: AtividadejuntaComponent}
  ];

