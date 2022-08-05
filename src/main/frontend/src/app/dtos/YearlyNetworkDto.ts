import {ArtistDto} from "./ArtistDto";
import {YearlyLinkDto} from "./YearlyLinkDto";

export class YearlyNetworkDto {
  constructor(
    public nodes: ArtistDto[],
    public links: YearlyLinkDto[]
  ){}
}
