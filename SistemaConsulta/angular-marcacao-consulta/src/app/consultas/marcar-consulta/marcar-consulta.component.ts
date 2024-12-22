import { Component, OnDestroy, OnInit } from '@angular/core';
import { EspecialidadesComponent } from "./especialidades/especialidades.component";
import { Consulta } from '../../shared/model/consulta';
import { Especialidade } from '../../shared/model/especialidade';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { PostosComponent } from './postos/postos.component';
import { Posto } from '../../shared/model/posto';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../shared/confirmation-dialog/confirmation-dialog.component';
import { ConsultasComponent } from './consultas/consultas.component';
import { ConsultasService } from '../service/consultas.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UsersService } from '../../auth/services/users.service';
import { User } from '../../auth/model/user';
import { firstValueFrom, Observable } from 'rxjs';

@Component({
  selector: 'app-marcar-consulta',
  standalone: true,
  imports: [EspecialidadesComponent, MatIconModule, MatButtonModule, MatCardModule, PostosComponent, CommonModule, ConsultasComponent, MatSnackBarModule],
  templateUrl: './marcar-consulta.component.html',
  styleUrl: './marcar-consulta.component.scss'
})
export class MarcarConsultaComponent implements OnInit, OnDestroy{
  consulta!: Consulta | null;
  speciality!: Especialidade | null;
  posto!: Posto | null;
  page: string = "especialidade";
  patient: User | null = null;

  constructor(
    public dialog: MatDialog,
    public consultaService: ConsultasService,
    private snackBar: MatSnackBar,
    private router: Router,
    private usersService: UsersService
  ) {
  }

  onSelectSpeciality(speciality: Especialidade){
    this.speciality = speciality;
    this.page = "posto";
  }

  onSelectPosto(posto: Posto){
    this.posto = posto;
    this.page = "consulta";
  }

  async onSelectConsulta(consulta: Consulta) {
    try {
      this.consulta = consulta;

      const userId = localStorage.getItem("user_id");
      if (!userId) {
        throw new Error("Usuário não encontrado no localStorage.");
      }

      // Carrega o usuário pelo ID
      this.patient = await firstValueFrom(this.usersService.loadById(userId));

      if (this.patient) {
        this.consulta.patient = this.patient;
      } else {
        console.warn("Nenhum paciente foi encontrado.");
      }
    } catch (error) {
      console.error("Erro ao carregar o paciente:", error);
    }
  }


  onMarcarConsulta() {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: `Deseja realmente marcar a consulta?

            Especialidade: ${this.speciality?.name}
            Posto: ${this.posto?.name}
            Endereço: ${this.posto?.address}
            Profissional: ${this.consulta?.doctorName}
            Data e hora: ${this.consulta?.date}`,
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.consultaService.marcarConsulta(this.consulta!).subscribe(
          () => {
            this.snackBar.open('Consulta marcada com sucesso!', 'Fechar', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
            this.router.navigate(['/consultas/list']);
          },
          (error) => {
            console.error('Erro ao marcar consulta:', error);
            this.snackBar.open('Erro ao marcar a consulta. Tente novamente.', 'Fechar', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          }
        );
      }
    });
  }

  onBack(){
    if(this.page.includes('posto')){
      this.page = "especialidade";
      this.speciality = null;
    }else{
      this.page = "posto";
      this.posto = null;
      this.consulta = null;
    }
  }


  ngOnDestroy(): void {
    this.consulta = null;
    this.speciality = null;
    this.posto = null;
  }

  ngOnInit(): void {
    this.consulta = null;
    this.speciality = null;
    this.posto = null;
  }
}
