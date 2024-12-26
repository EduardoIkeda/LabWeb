import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';


@NgModule({
  exports:
    [
      MatButtonModule,
      MatCardModule,
      MatIconModule,
      MatTableModule,
      MatToolbarModule,
      MatProgressSpinnerModule,
      CommonModule,
      MatSnackBarModule
    ],
})
export class AppMaterialModule { }
