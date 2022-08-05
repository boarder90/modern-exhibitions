
export class YearlyLinkDto {
  constructor(
    public source: number,
    public target: number,
    public startYear: number,
    public endYear: number,
    public numExhibitions: number,
    public cities: string[],
    public countries: string[]
  ){}
}
