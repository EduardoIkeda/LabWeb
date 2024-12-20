import { Consulta } from "./consulta";

export class Medico {
  id: string;
  name: string;
  crm: string;
  appointments: Consulta[];

  constructor(
    id: string,
    name: string,
    crm: string,
    appointments: Consulta[]
  ) {
    this.id = id;
    this.name = name;
    this.crm = crm;
    this.appointments = appointments;
  }
}
