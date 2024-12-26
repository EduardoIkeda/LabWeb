export interface AnosComConsultas {
  year: number;
  monthlyStats: MonthlyAppointmentStats[];
}

export interface MonthlyAppointmentStats {
  month: number;
  scheduledCount: number;
  attendedCount: number;
  missedCount: number;
  cancelledCount: number;
}

export interface ConsultasReport {
  name: string; // Consultas marcadas, canceladas, etc
  series: Report[];
}

export interface Report {
  name: string; // Mes, ano, etc
  value: number; // Numero de consultas
}

export interface EspecialidadeReport {
  specialtyName: string;
  appointmentsCount: number;
}
