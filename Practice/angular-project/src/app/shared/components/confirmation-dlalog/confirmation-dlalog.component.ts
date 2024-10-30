import { Component, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AppMaterialModule } from '../../app-material/app-material.module';

@Component({
  selector: 'app-confirmation-dlalog',
  standalone: true,
  imports: [MatDialogModule, AppMaterialModule],
  templateUrl: './confirmation-dlalog.component.html',
  styleUrl: './confirmation-dlalog.component.scss',
})
export class ConfirmationDlalogComponent {
  readonly dialogRef = inject(MatDialogRef<ConfirmationDlalogComponent>);
  readonly data = inject<string>(MAT_DIALOG_DATA);

  onNoClick(): void {
    this.dialogRef.close();
  }

  onConfirm(result: boolean)
  {
    this.dialogRef.close(result);
  }
}
