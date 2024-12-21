import { PostoSaudeService } from '../../../shared/service/health-center.service';
import { ConfirmationDialogComponent } from './../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { Component } from '@angular/core';
import { PostoSaudeListComponent } from "../../components/posto-saude-list/posto-saude-list.component";
import { HealthCenter } from '../../../shared/model/health-center';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PageEvent } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-posto-saude',
  standalone: true,
  imports: [PostoSaudeListComponent, MatCardModule],
  templateUrl: './posto-saude.component.html',
  styleUrl: './posto-saude.component.scss',
})
export class PostoSaudeComponent {
  healthCenters: HealthCenter[] = [];

  constructor(
    public readonly dialog: MatDialog,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly snackBar: MatSnackBar,
    private readonly healthCenterService: PostoSaudeService
  ) {
    this.refresh();
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.healthCenterService.list().subscribe((healthCentersList: HealthCenter[]) => {
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
          }
        });
      }
    });
  }

  onEditDoctor(healthCenter: HealthCenter) {
    this.router.navigate(['editmedico', healthCenter.id], { relativeTo: this.route });
  }
}
