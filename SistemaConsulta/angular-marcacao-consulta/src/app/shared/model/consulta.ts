import { User } from "../../auth/model/user";

export class Consulta {
  id: string;
  appointmentDateTime: Date;
  appointmentStatus: string;
  patientId: string | null;
  doctorName: string;
  specialization: string;
  isTomorrow: boolean = false;
  isFinalized: boolean = false;

  constructor(
    id: string,
    appointmentDateTime: Date,
    appointmentStatus: string,
    patientId: string | null,
    doctorName: string,
    specialization: string,
    isTomorrow: boolean,
    isFinalized: boolean
  ) {
    this.id = id;
    this.appointmentDateTime = appointmentDateTime;
    this.appointmentStatus = appointmentStatus;
    this.patientId = patientId;
    this.doctorName = doctorName;
    this.specialization = specialization;
    this.isTomorrow = isTomorrow;
    this.isFinalized = isFinalized;
  }
}
