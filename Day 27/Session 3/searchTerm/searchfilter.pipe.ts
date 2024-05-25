import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(items:any[],searchTerm:string): any[] {
    if(items.length>0 && searchTerm){
      var newARR:any[] = items.filter(item=>JSON.stringify(item).toLowerCase().includes(searchTerm.toLowerCase()));
      return newARR;
    }else{
      return items;
    }
  }
}
