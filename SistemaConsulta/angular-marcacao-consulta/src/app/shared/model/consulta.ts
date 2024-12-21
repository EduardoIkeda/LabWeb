export class Consulta {
  id: string;
  patientName: string;
  doctorName: string;
  date: Date;
  specialization: string;
  isTomorrow: boolean = false;
  isFinalized: boolean = false;

  constructor(
    id: string,
    patientName: string,
    doctorName: string,
    date: Date,
    specialization: string,
    isTomorrow: boolean,
    isFinalized: boolean
  ) {
    this.id = id;
    this.patientName = patientName;
    this.doctorName = doctorName;
    this.date = date;
    this.specialization = specialization;
    this.isTomorrow = isTomorrow;
    this.isFinalized = isFinalized;
  }
}
