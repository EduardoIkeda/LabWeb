import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category',
  standalone: true,
})
export class CategoryPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'Frontend':
        return 'code';
      case 'Backend':
        return 'dns';
      case 'Database':
        return 'analytics';
      default:
        return 'code';
    }
  }
}
