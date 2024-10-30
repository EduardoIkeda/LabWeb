import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from './app-material/app-material.module';
import { ConfirmationDlalogComponent } from './components/confirmation-dlalog/confirmation-dlalog.component';
import { ErrorDialogComponent } from './components/error-dialog/error-dialog.component';
import { CategoryPipe } from './pipe/category.pipe';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ErrorDialogComponent,
    AppMaterialModule,
    CategoryPipe,
    ConfirmationDlalogComponent,
  ],
  exports: [ErrorDialogComponent, CategoryPipe, ConfirmationDlalogComponent],
})
export class SharedModule {}
