import { Consulta } from "./consulta";

export interface Doctor {
  id: string;
  name: string;
  crm: string;
  appointments: Consulta[];
}
