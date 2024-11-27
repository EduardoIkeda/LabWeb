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
    paciente: string,
    medico: string,
    data: Date,
    especialidade: string
  ) {
    this.id = id;
    this.patientName = paciente;
    this.doctorName = medico;
    this.date = data;
    this.specialization = especialidade;
  }
}
