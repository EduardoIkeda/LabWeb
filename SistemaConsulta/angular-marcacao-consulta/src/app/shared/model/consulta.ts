import { User } from "../../auth/model/user";

export class Consulta {
  id: string;
  patient: User | null;
  doctorName: string;
  date: Date;
  specialization: string;
  isTomorrow: boolean = false;
  isFinalized: boolean = false;

  constructor(
    id: string,
    patient: User | null,
    doctorName: string,
    date: Date,
    specialization: string,
    isTomorrow: boolean,
    isFinalized: boolean
  ) {
    this.id = id;
    this.patient = patient;
    this.doctorName = doctorName;
    this.date = date;
    this.specialization = specialization;
    this.isTomorrow = isTomorrow;
    this.isFinalized = isFinalized;
  }
}
