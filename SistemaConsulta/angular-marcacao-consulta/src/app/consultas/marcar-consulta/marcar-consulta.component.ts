import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

import { UsersService } from '../../auth/services/users.service';
import { ConfirmationDialogComponent } from '../../shared/confirmation-dialog/confirmation-dialog.component';
import { Consulta } from '../../shared/model/consulta';
import { Especialidade } from '../../shared/model/especialidade';
import { HealthCenter } from '../../shared/model/health-center';
import { ConsultasService } from '../service/consultas.service';
import { ConsultasComponent } from './consultas/consultas.component';
import { EspecialidadesComponent } from './especialidades/especialidades.component';
import { PostosComponent } from './postos/postos.component';

@Component({
  selector: 'app-marcar-consulta',
  standalone: true,
  imports: [EspecialidadesComponent, MatIconModule, MatButtonModule, MatCardModule, PostosComponent, CommonModule, ConsultasComponent, MatSnackBarModule],
  templateUrl: './marcar-consulta.component.html',
  styleUrl: './marcar-consulta.component.scss'
})
export class MarcarConsultaComponent implements OnInit, OnDestroy {
  consulta!: Consulta | null;
  speciality!: Especialidade | null;
  healthCenter!: HealthCenter | null;
  page: string = "especialidade";
  bloqueado: boolean = false;

  constructor(
    public dialog: MatDialog,
    public consultaService: ConsultasService,
    private readonly snackBar: MatSnackBar,
    private readonly router: Router,
    private readonly usersService: UsersService
  ) {
  }

  onSelectSpeciality(speciality: Especialidade) {
    this.speciality = speciality;
    this.page = "posto";
  }

  onSelectHealthCenter(healthCenter: HealthCenter) {
    this.healthCenter = healthCenter;
    this.page = "consulta";
  }

  onSelectConsulta(consulta: Consulta) {
    this.consulta = consulta;

    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userId = localStorage.getItem("userId");

      if (!userId) {
        throw new Error("Usuário não encontrado no localStorage.");
      }

      this.consulta.patientId = userId;
    }

    console.log(this.consulta);
  }


  onMarcarConsulta() {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userStatus = localStorage.getItem("userStatus");
      if (userStatus === "Bloqueado") {
        this.bloqueado = true;

        this.snackBar.open('Você está bloqueado de fazer marcações!', 'Fechar', {
          duration: 5000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
        });
        this.router.navigate(['/consultas/list']);
      }
    }

    if (!this.bloqueado) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: `Deseja realmente marcar a consulta?

              Especialidade: ${this.speciality?.name}
              Posto: ${this.healthCenter?.name}
              Endereço: ${this.healthCenter?.address}
              Profissional: ${this.consulta?.doctorName}
              Data e hora: ${this.consulta?.appointmentDateTime}`,
      });

      dialogRef.afterClosed().subscribe((result: boolean) => {
        if (result && this.consulta != null) {
          this.consultaService.marcarConsulta(this.consulta).subscribe({
            next: () => this.onSuccess(),
            error: (error) => this.onError(error)
          });
        }
      });
    }
  }

  private onSuccess() {
    this.snackBar.open('Consulta marcada com sucesso!', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
    this.router.navigate(['/consultas/list']);
  }

  private onError(error: any) {
    console.error('Erro ao marcar consulta:', error);

    this.snackBar.open('Erro ao marcar a consulta. Tente novamente.', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }

  onBack() {
    if (this.page.includes('posto')) {
      this.page = "especialidade";
      this.speciality = null;
    } else {
      this.page = "posto";
      this.healthCenter = null;
      this.consulta = null;
    }
  }

  ngOnInit(): void {
    this.consulta = null;
    this.speciality = null;
    this.healthCenter = null;
  }

  ngOnDestroy(): void {
    this.consulta = null;
    this.speciality = null;
    this.healthCenter = null;
  }
}
