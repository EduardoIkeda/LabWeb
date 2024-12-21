import { Doctor } from "../../shared/model/doctor";

export class ConsultaPorData {
  date: string;
  doctors: Doctor[];

  constructor(
    date: string,
    doctors: Doctor[]
  ) {
    this.date = date;
    this.doctors = doctors;
  }
}
