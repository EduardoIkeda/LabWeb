import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostoSaudeListComponent } from './posto-saude-list.component';

describe('PostoSaudeListComponent', () => {
  let component: PostoSaudeListComponent;
  let fixture: ComponentFixture<PostoSaudeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostoSaudeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostoSaudeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
