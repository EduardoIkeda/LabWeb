import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvancePieChartComponent } from './advance-pie-chart.component';

describe('AdvancePieChartComponent', () => {
  let component: AdvancePieChartComponent;
  let fixture: ComponentFixture<AdvancePieChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvancePieChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdvancePieChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
