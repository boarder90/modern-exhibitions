import {ArtistDto} from "./ArtistDto";
import {LinkDto} from "./LinkDto";

export class NetworkDto {
  constructor(
    public nodes: ArtistDto[],
    public links: LinkDto[]
){}
}
