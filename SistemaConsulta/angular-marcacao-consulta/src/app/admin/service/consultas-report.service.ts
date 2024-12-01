import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConsultasReport } from '../model/consultas-report';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Month } from '../../shared/enum/month-enum';
import { AnosComConsultas } from '../model/anos-com-consulta';

@Injectable({
  providedIn: 'root',
})
export class ConsultasReportService {
  private readonly API = 'assets/consultas-report.json';

  constructor(private readonly http: HttpClient) {}

  // TODO Fazer a requisição selecionando ano pro backend

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

  getConsultasMarcadasPorMes(ano: number): Observable<any[]> {
    return this.http
      .get<any>(this.API)
      .pipe(map((data) => data['ConsultasMarcadasPorMes']));
  }

  getAnosComConsultas(): Observable<AnosComConsultas[]> {
    return this.http
      .get<any>(this.API)
      .pipe(map((data) => data['AnosComConsultas']));
  }
}
