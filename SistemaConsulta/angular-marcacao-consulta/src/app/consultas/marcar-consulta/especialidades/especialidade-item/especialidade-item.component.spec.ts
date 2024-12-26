import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspecialidadeItemComponent } from './especialidade-item.component';

describe('EspecialidadeItemComponent', () => {
  let component: EspecialidadeItemComponent;
  let fixture: ComponentFixture<EspecialidadeItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EspecialidadeItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EspecialidadeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
