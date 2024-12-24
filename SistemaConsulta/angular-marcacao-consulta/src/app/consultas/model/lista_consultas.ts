import { ConsultaPorData } from "./consulta_por_data";

export class ListaConsultas {
  listAppointmentsPerDate: ConsultaPorData[];

  constructor(
    listAppointmentsPerDate: ConsultaPorData[]
  ) {
    this.listAppointmentsPerDate = listAppointmentsPerDate;
  }
}
