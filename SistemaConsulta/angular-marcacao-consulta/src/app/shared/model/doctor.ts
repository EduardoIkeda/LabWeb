import { Consulta } from "./consulta";

export interface Doctor {
  id: string;
  doctorName: string;
  crm: string;
  startWork: string;
  endWork: string;
  workingDays: string[];
  doctorAppointments: Consulta[];
}
