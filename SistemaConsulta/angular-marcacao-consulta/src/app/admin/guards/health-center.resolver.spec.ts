/* tslint:disable:no-unused-variable */

import { TestBed, inject } from '@angular/core/testing';
import { PostoSaudeResolver } from './health-center.resolver';

describe('Service: PostoSaude', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PostoSaudeResolver],
    });
  });

  it('should ...', inject(
    [PostoSaudeResolver],
    (service: PostoSaudeResolver) => {
      expect(service).toBeTruthy();
    }
  ));
});
