import { ConfirmationDialogComponent } from './../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { Component } from '@angular/core';
import { PostoSaudeListComponent } from "../../components/posto-saude-list/posto-saude-list.component";
import { PostoSaude } from '../../../shared/model/posto-saude';
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
  postos: PostoSaude[] = [];

  constructor(
    public readonly dialog: MatDialog,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly snackBar: MatSnackBar
  ) {
    this.refresh();
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.postos = [
      {
        id: '1',
        nome: 'Posto de Saúde 1',
        endereco: 'Rua 1, 123',
        horarioAbertura: '08:00',
        horarioFechamento: '17:00',
        especialidades: [
          'Cardiologia',
          'Dermatologia',
          'Pediatria',
          'Ortopedia',
        ],
      },
      {
        id: '2',
        nome: 'Posto de Saúde 2',
        endereco: 'Rua 2, 456',
        horarioAbertura: '09:00',
        horarioFechamento: '18:00',
        especialidades: [
          'Cardiologia',
          'Dermatologia',
          'Pediatria',
          'Ortopedia',
        ],
      },
    ];

    // this.courses$ = this.courseService
    //   .list(pageEvent.pageIndex, pageEvent.pageSize)
    //   .pipe(
    //     tap(() => {
    //       this.pageIndex = pageEvent.pageIndex;
    //       this.pageSize = pageEvent.pageSize;
    //     }),
    //     catchError((error) => {
    //       this.onError('Erro ao carregar a lista de cursos');
    //       return of({ courses: [], totalElements: 0, totalPages: 0 });
    //     })
    //   );
  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  onEdit(postoSaude: PostoSaude) {
    this.router.navigate(['edit', postoSaude.id], { relativeTo: this.route });
  }

  onRemove(postoSaude: PostoSaude) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Deseja realmente excluir o curso ' + postoSaude.nome + '?',
    });
    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        // this.courseService.remove(course.id).subscribe({
        //   next: () => {
        //     this.refresh();
        //     this.snackBar.open('Curso removido com sucesso', 'X', {
        //       duration: 5000,
        //       verticalPosition: 'top',
        //       horizontalPosition: 'center',
        //     });
        //   },
        //   error: () => this.onError('Erro ao tentar remover curso.'),
        // });
      }
    });
  }
}
