import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'cityPipe'
})
export class CityPipe implements PipeTransform {

  transform(city: string, args?: any): String | undefined {

    let cityString;
    cityString = city.replace(/\s/g, "");
    cityString = cityString.replace(",","");
    cityString = cityString.replace("(","");
    cityString = cityString.replace(")","");

     return cityString;
  }
}
