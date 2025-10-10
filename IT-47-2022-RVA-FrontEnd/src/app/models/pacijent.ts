import { Odeljenje } from "./odeljenje";
import { Dijagnoza } from "./dijagnoza";

export class Pacijent {

  id!: number;
  ime!: string;
  zdrOsiguranje!: boolean;
  datumRodjenja!: Date;
  odeljenje!: Odeljenje;
  dijagnoza!: Dijagnoza;
}