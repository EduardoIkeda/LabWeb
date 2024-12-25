import { Consulta } from "./consulta";
import { Especialidade } from "./especialidade";

export interface Doctor {
  id: string;
  name: string;
  crm: string;
  startWork: string;
  endWork: string;
  avatarUrl: string;
  workingDays: string[];
  especialidade: Especialidade[];
  doctorAppointments: Consulta[];
}
