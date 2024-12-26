import { Doctor } from "../../shared/model/doctor";

export class ConsultaPorData {
  date: Date;
  doctors: Doctor[];

  constructor(
    date: Date,
    doctors: Doctor[]
  ) {
    this.date = date;
    this.doctors = doctors;
  }
}
