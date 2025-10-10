import { Bolnica } from "./bolnica";

export class Odeljenje {

  id!: number;
  naziv!: string;
  lokacija!: string;
  bolnica!: Bolnica;
}