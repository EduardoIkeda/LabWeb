import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

import { Especialidade } from '../../../shared/model/especialidade';
import { HealthCenter } from '../../../shared/model/health-center';
import { HealthCenterService } from '../../../shared/service/health-center.service';
import { PostoItemComponent } from './posto-item/posto-item.component';

@Component({
  selector: 'app-postos',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatIconModule, PostoItemComponent],
  templateUrl: './postos.component.html',
  styleUrl: './postos.component.scss'
})
export class PostosComponent implements OnInit {
  @Output() selectHealthCenter = new EventEmitter<HealthCenter>();
  @Input() speciality!: Especialidade | null;
  healthCenters: HealthCenter[] = [];
  displayedHealthCenters: HealthCenter[] = [];
  query!: string;

  constructor(private readonly healthCenterService: HealthCenterService) {
  }

  ngOnInit(): void {
    this.loadHealthCenters();
  }

  loadHealthCenters() {
    this.healthCenterService.listBySpecialty(this.speciality!.id).subscribe({
      next: (healthCenters) => {
        this.healthCenters = healthCenters.map(
          (healthCenter) => ({ ...healthCenter })
        );
        this.displayedHealthCenters = this.healthCenters.sort((a, b) => (b.availableAppointmentsCount ?? 0) - (a.availableAppointmentsCount ?? 0));
      },
      error: (error: any) => console.error('Error:', error),
    });
  }

  onChange(event: any) {
    this.query = event.target.value.trim().toLowerCase();

    if (this.query != "") {
      this.displayedHealthCenters = [];
      for (let healthCenter of this.healthCenters) {
        if (healthCenter.name.toLowerCase().includes(this.query)) {
          this.displayedHealthCenters.push(healthCenter);
        }
      }
    } else {
      this.displayedHealthCenters = this.healthCenters.sort((a, b) => (b.availableAppointmentsCount ?? 0) - (a.availableAppointmentsCount ?? 0));
    }
  }

  onSelectHealthCenter(healthCenter: HealthCenter) {
    this.selectHealthCenter.emit(healthCenter);
  }
}
