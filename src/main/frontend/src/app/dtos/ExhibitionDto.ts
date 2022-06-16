import {LocationDto} from "./LocationDto";

export class ExhibitionDto {
  constructor(
    public startDate: string,
    public endDate: string,
    public startYear: number,
    public endYear: number,
    public title: string,
    public numArtists: number,
    public numCatalogueEntries: number,
    public id: number,
    public locations: LocationDto[]
  ){}
}
