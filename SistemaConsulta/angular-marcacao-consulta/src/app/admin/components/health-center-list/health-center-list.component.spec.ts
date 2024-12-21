import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthCenterListComponent } from './health-center-list.component';

describe('PostoSaudeListComponent', () => {
  let component: HealthCenterListComponent;
  let fixture: ComponentFixture<HealthCenterListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HealthCenterListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HealthCenterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
