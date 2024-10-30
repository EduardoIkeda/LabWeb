import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AtividadejuntaComponent } from './atividadejunta.component';

describe('AtividadejuntaComponent', () => {
  let component: AtividadejuntaComponent;
  let fixture: ComponentFixture<AtividadejuntaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AtividadejuntaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AtividadejuntaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
