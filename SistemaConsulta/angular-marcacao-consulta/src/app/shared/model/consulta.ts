export class Consulta {
  id: string;
  appointmentDateTime: Date;
  appointmentStatus: string;
  patientId: string | null;
  doctorName: string;
  specialtyName: string;
  healthCenterName: string;
  healthCenterAddress: string;
  isTomorrow: boolean = false;
  isFinalized: boolean = false;

  constructor(
    id: string,
    appointmentDateTime: Date,
    appointmentStatus: string,
    patientId: string | null,
    doctorName: string,
    specialtyName: string,
    healthCenterName: string,
    healthCenterAddress: string,
    isTomorrow: boolean,
    isFinalized: boolean
  ) {
    this.id = id;
    this.appointmentDateTime = appointmentDateTime;
    this.appointmentStatus = appointmentStatus;
    this.patientId = patientId;
    this.doctorName = doctorName;
    this.specialtyName = specialtyName;
    this.healthCenterName = healthCenterName;
    this.healthCenterAddress = healthCenterAddress;
    this.isTomorrow = isTomorrow;
    this.isFinalized = isFinalized;
  }
}
