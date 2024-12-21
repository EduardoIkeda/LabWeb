import { ConsultaPorData } from "./consulta_por_data";

export class ListaConsultas {
  speciality: string;
  healthCenter: string;
  listAppointmentsPerDate: ConsultaPorData[];

  constructor(
    speciality: string,
    healthCenter: string,
    listAppointmentsPerDate: ConsultaPorData[]
  ) {
    this.speciality = speciality;
    this.healthCenter = healthCenter;
    this.listAppointmentsPerDate = listAppointmentsPerDate;
  }
}
