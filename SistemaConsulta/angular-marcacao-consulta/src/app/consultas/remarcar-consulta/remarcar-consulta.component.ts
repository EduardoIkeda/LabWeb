import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Consulta } from '../../shared/model/consulta';
import { ConsultasService } from '../service/consultas.service';
import { ConsultasListService } from '../marcar-consulta/consultas/services/consultas.service';
import { firstValueFrom } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ListaConsultas } from '../model/lista_consultas';
import { ConsultaPorData } from '../model/consulta_por_data';
import { ConfirmationDialogComponent } from '../../shared/confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

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
  listaConsultas!: ListaConsultas;
  listaPorData: ConsultaPorData[] = [];
  displayedListaPorData: ConsultaPorData[] = [];
  selectedDate: Date | null = null;

  constructor(
    private readonly route: ActivatedRoute,
    private router: Router,
    private readonly consultasService: ConsultasService,
    private readonly consultasListService: ConsultasListService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    // Aguarda o parâmetro da rota e carrega a consulta.
    this.route.paramMap.subscribe(async (params) => {
      this.consulta_id = params.get('id');

      if (this.consulta_id) {
        await this.getConsulta(this.consulta_id);
      }

      if(this.consulta){
        // VAI PRECISAR DO ID DO POSTO E ESPECIALIDADE NO MODEL DE CONSULTA
        this.consultasListService.list("1", "1").subscribe({
          next: (data) => {
            this.listaConsultas = data;
            this.listaPorData = this.listaConsultas.listAppointmentsPerDate;
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

  limparData(){
    this.selectedDate = null;
    this.onDateChange();
  }

  convertToDate(dateString: string): Date {
    const [datePart, timePart] = dateString.split(' ');
    const [day, month, year] = datePart.split('/');
    const [hours, minutes] = timePart.split(':');

    // Retorna o objeto Date com os componentes de data e hora
    return new Date(+year, +month - 1, +day, +hours, +minutes);
  }

  async onSelect(event: Event, consulta_nova: Consulta) {

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
          this.consulta.patientId = null;
          // VER QUESTÃO DO ENDPOINT PARA REMARCAÇÃO / MUDAR STATUS DA CONSULTA QUE VAI FICAR SEM USER
          this.consultasService.marcarConsulta(this.consulta).subscribe({
            next: () => {
              this.consultasService.marcarConsulta(consulta_nova).subscribe({
                next: () => this.onSuccess(),
                error: (error) => this.onError(error)
              })
            },
            error: (error) => this.onError(error)
          });
        }
      });
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
}
