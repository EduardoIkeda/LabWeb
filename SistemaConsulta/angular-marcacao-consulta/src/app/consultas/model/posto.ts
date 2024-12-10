export class Posto {
  id: string;
  name: string;
  address: string;
  appointments: number;

  constructor(
    id: string,
    name: string,
    address: string,
    appointments: number
  ) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.appointments = appointments
  }
}
