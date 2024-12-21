import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthCenterFormComponent } from './health-center-form.component';

describe('PostoSaudeFormComponent', () => {
  let component: HealthCenterFormComponent;
  let fixture: ComponentFixture<HealthCenterFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HealthCenterFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HealthCenterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
