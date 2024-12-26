import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicoConsultaComponent } from './medico-consulta.component';

describe('MedicoConsultaComponent', () => {
  let component: MedicoConsultaComponent;
  let fixture: ComponentFixture<MedicoConsultaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicoConsultaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicoConsultaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
