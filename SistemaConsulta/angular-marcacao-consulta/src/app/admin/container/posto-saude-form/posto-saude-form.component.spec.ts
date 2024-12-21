import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostoSaudeFormComponent } from './posto-saude-form.component';

describe('PostoSaudeFormComponent', () => {
  let component: PostoSaudeFormComponent;
  let fixture: ComponentFixture<PostoSaudeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostoSaudeFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostoSaudeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
