import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationDlalogComponent } from './confirmation-dlalog.component';

describe('ConfirmationDlalogComponent', () => {
  let component: ConfirmationDlalogComponent;
  let fixture: ComponentFixture<ConfirmationDlalogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmationDlalogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmationDlalogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
