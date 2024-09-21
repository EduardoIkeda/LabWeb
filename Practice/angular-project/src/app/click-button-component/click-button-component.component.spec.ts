import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClickButtonComponentComponent } from './click-button-component.component';

describe('ClickButtonComponentComponent', () => {
  let component: ClickButtonComponentComponent;
  let fixture: ComponentFixture<ClickButtonComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClickButtonComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClickButtonComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
