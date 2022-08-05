export class LinkDto {
  constructor(
  public source: number,
  public target: number,
  public startYears: number[],
  public endYears: number[],
  public numExhibitions: number,
  public cities: string[],
  public countries: string[]
  ){}
}
