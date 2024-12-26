import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { map } from 'rxjs/operators';

import { Month } from '../../shared/enum/month-enum';
import {
  AnosComConsultas,
  ConsultasReport,
  EspecialidadeReport,
} from '../model/consultas-report';

@Injectable({
  providedIn: 'root',
})
export class ConsultasReportService {
  private readonly API = 'assets/consultas-report.json';

  constructor(private readonly http: HttpClient) {}

  getConsultasCanceladasNoAno(ano: number): Observable<ConsultasReport[]> {
    return this.http.get<any>(this.API).pipe(
      map((data) => {
        const reports = data['ConsultaCanceladaNoAno'] as ConsultasReport[];
        return reports.map((report) => {
          report.series.forEach((serie) => {
            const monthInt = parseInt(serie.name, 10);
            if (!isNaN(monthInt) && Month[monthInt] !== undefined) {
              serie.name = Month[monthInt];
            }
          });
          return report;
        });
      })
    );
  }

  getConsultasMarcadasPorMes(year: number, month: number): Observable<Report[]> {
    return this.http
      .get<any>(this.API)
      .pipe(map((data) => data['ConsultasMarcadasPorMes']));
  }

  // Pronto
  getAnosComConsultas(): Observable<AnosComConsultas[]> {
    return this.http.get<AnosComConsultas[]>('api/appointments/years-with-appointments');
  }

  // Pronto
  getEspecialidadesMaisConsultadas(year: number): Observable<EspecialidadeReport[]> {
    return this.http.get<EspecialidadeReport[]>(`/api/specialties/count/${year}`);
  }

  getEspecialidadesMaisConsultadasPorPosto(): Observable<
    EspecialidadeReport[]
  > {
    return this.http.get<any>(this.API).pipe(
      map((data) =>
        data['EspecialidadesMaisConsultadasPorPosto'].map(
          (especialidade: any) => ({
            nome: especialidade.name,
            quantidade: especialidade.value,
          })
        )
      )
    );
  }
}
