/* tslint:disable:no-unused-variable */

import { TestBed, inject } from '@angular/core/testing';
import { PostoSaudeService } from './health-center.service';

describe('Service: PostoSaude', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PostoSaudeService]
    });
  });

  it('should ...', inject([PostoSaudeService], (service: PostoSaudeService) => {
    expect(service).toBeTruthy();
  }));
});
