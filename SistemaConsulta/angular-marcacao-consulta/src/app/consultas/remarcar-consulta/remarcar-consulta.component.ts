import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';

import { ConfirmationDialogComponent } from '../../shared/confirmation-dialog/confirmation-dialog.component';
import { Consulta } from '../../shared/model/consulta';
import { ConsultaPorData } from '../model/consulta_por_data';
import { ConsultasService } from '../service/consultas.service';

@Component({
  selector: 'app-remarcar-consulta',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './remarcar-consulta.component.html',
  styleUrl: './remarcar-consulta.component.scss',
})
export class RemarcarConsultaComponent implements OnInit {
  consulta_id!: string | null;
  consulta!: Consulta | null; // Permite inicializar vazio.
  listaPorData: ConsultaPorData[] = [];
  displayedListaPorData: ConsultaPorData[] = [];
  selectedDate: Date | null = null;
  bloqueado: boolean = false;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly consultasService: ConsultasService,
    public dialog: MatDialog,
    private readonly snackBar: MatSnackBar,
    private readonly location: Location
  ) { }

  ngOnInit() {
    // Aguarda o parâmetro da rota e carrega a consulta.
    this.route.paramMap.subscribe(async (params) => {
      this.consulta_id = params.get('id');

      if (this.consulta_id) {
        await this.getConsulta(this.consulta_id);
      }

      if (this.consulta) {
        this.consultasService.listGroup(this.consulta.specialtyId, this.consulta.healthCenterId).subscribe({
          next: (data) => {
            this.listaPorData = data;
            this.displayedListaPorData = this.listaPorData;
          },
          error: (error) => console.error('Error:', error),
        });
      }
    });
  }

  async getConsulta(id: string) {
    try {
      this.consulta = await firstValueFrom(this.consultasService.loadById(id));


      if (!this.consulta) {
        console.error(`Consulta com ID ${id} não encontrada.`);
      }
    } catch (error) {
      console.error('Erro ao carregar consulta:', error);
    }
  }

  async onSelect(event: Event, consulta_nova: Consulta) {
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
        data: `Deseja realmente remarcar a consulta?

                Especialidade: ${consulta_nova.specialtyName}
                Posto: ${consulta_nova.healthCenterName}
                Endereço: ${consulta_nova.healthCenterAddress}
                Profissional: ${consulta_nova.doctorName}
                Data e hora: ${consulta_nova.appointmentDateTime}`,
      });

      dialogRef.afterClosed().subscribe((result: boolean) => {
        if (result && consulta_nova != null && this.consulta != null) {
          consulta_nova.patientId = this.consulta.patientId;

          this.consultasService.cancelarConsulta(this.consulta).subscribe({
            next: () => {
              console.log("Consulta desmarcada com sucesso");
              this.consultasService.marcarConsulta(consulta_nova).subscribe({
                next: () => this.onSuccess(),
                error: (error) => this.onError(error)
              })
            },
            error: (error) => this.onError(error)
          });
          console.log("Consulta marcada com sucesso com o id:" + consulta_nova.id);
        }
      });
    }
  }

  private onSuccess() {
    this.snackBar.open('Consulta remarcada com sucesso!', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
    this.router.navigate(['/consultas/list']);
  }

  private onError(error: any) {
    console.error('Erro ao remarcar consulta:', error);

    this.snackBar.open('Erro ao remarcar a consulta. Tente novamente.', 'Fechar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }

  onBack() {
    this.location.back();
  }

  limparData() {
    this.selectedDate = null;
    this.onDateChange();
  }

  onDateChange() {
    if (this.selectedDate) {
      // Zera a hora, minuto, segundo e milissegundo da data selecionada
      const resetSelectedDate = new Date(this.selectedDate.setHours(0, 0, 0, 0));

      this.displayedListaPorData = this.listaPorData.filter(data => {
        // Verifica se data.date é uma string e converte para um objeto Date
        const dataDate = this.convertToDate(data.date.toString());

        // Zera a hora de dataDate para comparação
        const resetDataDate = new Date(dataDate.setHours(0, 0, 0, 0));
        return resetDataDate.getTime() === resetSelectedDate.getTime(); // Compara as datas sem as horas
      });
    } else {
      // Se nenhuma data estiver selecionada, mostra todas as consultas
      this.displayedListaPorData = this.listaPorData;
    }
  }

  convertToDateTime(dateString: string): Date {
    const [datePart, timePart] = dateString.split(' ');
    const [day, month, year] = datePart.split('/');
    const [hours, minutes] = timePart.split(':');

    // Retorna o objeto Date com os componentes de data e hora
    return new Date(+year, +month - 1, +day, +hours, +minutes);
  }

  convertToDate(dateString: string): Date {
    const [day, month, year] = dateString.split('/');

    // Retorna o objeto Date com os componentes de data
    return new Date(+year, +month - 1, +day);
  }
}
