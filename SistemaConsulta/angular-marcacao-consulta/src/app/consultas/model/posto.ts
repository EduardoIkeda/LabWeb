import { Consulta } from "./consulta";

export class Posto {
  id: string;
  name: string;
  address: string;
  appointments: Consulta[];

  constructor(
    id: string,
    name: string,
    address: string,
    appointments: Consulta[]
  ) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.appointments = appointments
  }
}
