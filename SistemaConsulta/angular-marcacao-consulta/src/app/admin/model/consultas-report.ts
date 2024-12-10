export interface ConsultasReport {
  name: string; // Consultas marcadas, canceladas, etc
  series: Report[];
}

export interface Report {
  name: string; // Mes, ano, etc
  value: number; // Numero de consultas
}

export interface EspecialidadeReport {
  id: number;
  nome: string;
  quantidade: number;
}

export interface AnosComConsultas {
  year: number;
  month: number[];
}
