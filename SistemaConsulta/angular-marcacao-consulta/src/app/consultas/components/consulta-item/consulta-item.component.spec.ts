import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaItemComponent } from './consulta-item.component';

describe('ConsultaItemComponent', () => {
  let component: ConsultaItemComponent;
  let fixture: ComponentFixture<ConsultaItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultaItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultaItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
