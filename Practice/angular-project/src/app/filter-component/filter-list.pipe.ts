import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterList',
  standalone: true
})
export class FilterListPipe implements PipeTransform {

  transform(value: any[], filterString: string, propName: string): any[] {
    if (!value || !filterString || !propName) {
      return value;
    }
    return value.filter(item => item[propName].toLowerCase().includes(filterString.toLowerCase()));
  }

}
