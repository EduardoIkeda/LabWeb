export class Consulta {
  id: string;
  paciente: string;
  medico: string;
  data: Date;
  especialidade: string;

  constructor(
    id: string,
    paciente: string,
    medico: string,
    data: Date,
    especialidade: string
  ) {
    this.id = id;
    this.paciente = paciente;
    this.medico = medico;
    this.data = data;
    this.especialidade = especialidade;
  }

  isTomorrow(): boolean {
    const today = new Date();
    const tomorrow = new Date();
    tomorrow.setDate(today.getDate() + 1);
    return this.data.getDate() === tomorrow.getDate();
  }

  isCompleted(): boolean {
    const now = new Date();
    return this.data.getTime() < now.getTime();
  }
}
