import { Medico } from "../../shared/model/medico";

export class ConsultaPorData {
  date: string;
  doctors: Medico[];

  constructor(
    date: string,
    doctors: Medico[]
  ) {
    this.date = date;
    this.doctors = doctors;
  }
}
