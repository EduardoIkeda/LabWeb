import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

import { ConfirmationDialogComponent } from '../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { HealthCenter } from '../../../shared/model/health-center';
import { HealthCenterService } from '../../../shared/service/health-center.service';
import { HealthCenterListComponent } from '../../components/health-center-list/health-center-list.component';

@Component({
  selector: 'app-health-center',
  standalone: true,
  imports: [HealthCenterListComponent, MatCardModule],
  templateUrl: './health-center.component.html',
  styleUrl: './health-center.component.scss',
})
export class PostoSaudeComponent {
  healthCenters: HealthCenter[] = [];

  constructor(
    public readonly dialog: MatDialog,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly snackBar: MatSnackBar,
    private readonly healthCenterService: HealthCenterService
  ) {
    this.refresh();
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.healthCenterService.list().subscribe(
      (healthCentersList: HealthCenter[]) => {
        this.healthCenters = healthCentersList;
      });
  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  onEdit(healthCenter: HealthCenter) {
    this.router.navigate(['edit', healthCenter.id], { relativeTo: this.route });
  }

  onRemove(healthCenter: HealthCenter) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Deseja realmente excluir o curso ' + healthCenter.name + '?',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.healthCenterService.remove(healthCenter.id).subscribe({
          next: () => {
            this.refresh();
            this.snackBar.open('Curso removido com sucesso', 'X', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          },
        });
      }
    });
  }

  onEditDoctor(healthCenter: HealthCenter) {
    this.router.navigate(['editmedico', healthCenter.id], {
      relativeTo: this.route,
    });
  }
}
