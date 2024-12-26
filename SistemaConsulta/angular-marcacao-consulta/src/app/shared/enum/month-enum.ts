export enum Month {
  Janeiro = 1,
  Fevereiro,
  Marco,
  Abril,
  Maio,
  Junho,
  Julho,
  Agosto,
  Setembro,
  Outubro,
  Novembro,
  Dezembro
}

export function getMonthName(monthNumber: number): string {
  return Month[monthNumber];
}
