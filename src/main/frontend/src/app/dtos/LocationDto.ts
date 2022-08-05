export class LocationDto {
  constructor(
    public city: string,
    public country: string,
    public longitude: number,
    public latitude: number,
    public id: number
  ){}
}
