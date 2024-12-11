/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PostoSaudeService } from './posto-saude.service';

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
