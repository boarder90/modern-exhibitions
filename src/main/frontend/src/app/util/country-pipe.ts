import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'countryPipe'
})
export class CountryPipe implements PipeTransform {

  transform(country: string, args?: any): String | undefined {

    switch (country) {
      case "DE":
        return "Germany";
      case "GB":
        return "Great Britain";
      case "FR":
        return "France";
      case "NL":
        return "Netherlands"
      case "UA":
        return "Ukraine";
      case "CH":
        return "Switzerland";
      case "AT":
        return "Austria";
      case "HU":
        return "Hungary";
      case "CZ":
        return "Czech Rep.";
      case "US":
        return "USA";
      case "BE":
        return "Belgium";
      case "IT":
        return "Italy";
      case "SE":
        return "Sweden";
      case "RU":
        return "Russia";
      case "NO":
        return "Norway";
      case "LV":
        return "Latvia";
      case "EE":
        return "Estonia";
      case "JP":
        return "Japan";
      case "PL":
        return "Poland";
      case "DL":
        return "Denmark";
      case "ES":
        return "Spain";
      default:
        return "";
    }
  }
}
