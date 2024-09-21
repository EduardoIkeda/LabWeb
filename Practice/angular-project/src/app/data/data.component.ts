import { Component } from '@angular/core';
import { DataServiceService } from '../data-service.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-data',
  standalone: true,
  imports: [NgFor],
  templateUrl: './data.component.html',
  styleUrl: './data.component.scss'
})
export class DataComponent {
  data: string[] = [];

  constructor(private dataService: DataServiceService) { }

  ngOnInit() {
    this.data = this.dataService.getData();
  }
}
