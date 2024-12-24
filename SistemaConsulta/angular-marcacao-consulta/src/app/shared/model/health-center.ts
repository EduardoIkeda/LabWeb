import { Especialidade } from "./especialidade";

export interface  HealthCenter {
  id: string | null;
  name: string;
  address: string;
  openingHour: string;
  closingHour: string;
  specialties: Especialidade[];
  availableAppointmentsCount: number | null;
}
