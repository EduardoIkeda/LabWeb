import { Component, OnDestroy, OnInit } from '@angular/core';
import { EspecialidadesComponent } from "./especialidades/especialidades.component";
import { Consulta } from '../model/consulta';
import { Especialidade } from '../model/especialidade';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { PostosComponent } from './postos/postos.component';
import { Posto } from '../model/posto';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-marcar-consulta',
  standalone: true,
  imports: [EspecialidadesComponent, MatIconModule, MatButtonModule, MatCardModule, PostosComponent, CommonModule],
  templateUrl: './marcar-consulta.component.html',
  styleUrl: './marcar-consulta.component.scss'
})
export class MarcarConsultaComponent implements OnInit, OnDestroy{
  consulta!: Consulta | null;
  speciality!: Especialidade | null;
  posto!: Posto | null;

  constructor(
    public dialog: MatDialog
  ) {
  }

  onSelectSpeciality(speciality: Especialidade){
    this.speciality = speciality;
    console.log(this.speciality.name);
  }

  onSelectPosto(posto: Posto){
    this.posto = posto;
    console.log(this.posto.name);
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
        // this.consultaService.marcarConsulta(this.consulta).subscribe(
        //   () => {
        //     this.snackBar.open('Consulta marcada com sucesso!', 'Fechar', {
        //       duration: 5000,
        //       verticalPosition: 'top',
        //       horizontalPosition: 'center',
        //     });
        //   },
        //   (error) => {
        //     console.error('Erro ao marcar consulta:', error);
        //     this.snackBar.open('Erro ao marcar a consulta. Tente novamente.', 'Fechar', {
        //       duration: 5000,
        //       verticalPosition: 'top',
        //       horizontalPosition: 'center',
        //     });
        //   }
        // );
      }
    });
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
