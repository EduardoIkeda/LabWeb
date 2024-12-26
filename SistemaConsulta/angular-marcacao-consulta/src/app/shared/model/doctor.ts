import { Consulta } from "./consulta";
import { Especialidade } from "./especialidade";

export interface Doctor {
  id: string;
  doctorName: string;
  crm: string;
  startWork: string;
  endWork: string;
  workingDays: string[];
  specialties: Especialidade[];
  doctorAppointments: Consulta[];
}
