import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostoItemComponent } from './posto-item.component';

describe('PostoItemComponent', () => {
  let component: PostoItemComponent;
  let fixture: ComponentFixture<PostoItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostoItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostoItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
