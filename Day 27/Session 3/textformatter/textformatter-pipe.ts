import { LowerCasePipe, TitleCasePipe, UpperCasePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'textFormatter'
})
export class TextFormatterPipe implements PipeTransform {
  private up:UpperCasePipe;
  private low:LowerCasePipe;
  //private cap:TitleCasePipe;
  
  constructor(){

  }

  transform(value: string,format:string,separator:string=' '): string {
    switch(format){
      case 'uppercase':
        return this.up.transform(value);
      case 'lowercase':
        return value.toLowerCase();
        //return this.low.transform(value);
      case 'capitalize':
        return value.split(
          ' '
          ).map(word=>word.charAt(
          0
          ).toUpperCase() + word.slice(
          1
          ).toLowerCase()).join(separator);
        //return this.cap.transform(value);
      case 'reverse':
        return value.split('').reverse().join('');
      default:
        return value;
    }
    
  }

}
