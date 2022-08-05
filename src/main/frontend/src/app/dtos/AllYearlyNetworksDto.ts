import {YearlyNetworkDto} from "./YearlyNetworkDto";

export class AllYearlyNetworksDto {
  constructor(
    public year: number,
    public network: YearlyNetworkDto
  ){}
}
