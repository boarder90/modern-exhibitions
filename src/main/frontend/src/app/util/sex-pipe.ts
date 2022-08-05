import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sexPipe'
})
export class SexPipe implements PipeTransform {

  transform(sex: string, args?: any): String | undefined {
    if(sex === "M"){
      return "Male";
    } else if (sex==="F"){
      return "Female";
    } else {
      return "Unknown";
    }
  }
}
