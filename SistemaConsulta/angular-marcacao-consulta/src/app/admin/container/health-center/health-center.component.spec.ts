import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostoSaudeComponent } from './health-center.component';

describe('PostoSaudeComponent', () => {
  let component: PostoSaudeComponent;
  let fixture: ComponentFixture<PostoSaudeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostoSaudeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostoSaudeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
