import { User } from "../../auth/model/user";

export class Consulta {
  id: string;
  patient: User | null;
  doctorName: string;
  appointmentDateTime: Date;
  //appointmentStatus: String;
  specialization: string;
  isTomorrow: boolean = false;
  isFinalized: boolean = false;

  constructor(
    id: string,
    patient: User | null,
    doctorName: string,
    appointmentDateTime: Date,
    //appointmentStatus: String,
    specialization: string,
    isTomorrow: boolean,
    isFinalized: boolean
  ) {
    this.id = id;
    this.patient = patient;
    this.doctorName = doctorName;
    this.appointmentDateTime = appointmentDateTime;
    //this.appointmentStatus = appointmentStatus
    this.specialization = specialization;
    this.isTomorrow = isTomorrow;
    this.isFinalized = isFinalized;
  }
}
