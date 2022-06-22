export class LocationDto {
  constructor(
    public venue: string,
    public city: string,
    public country: string,
    public longitude: number,
    public latitude: number,
    public id: number
  ){}
}
