/* tslint:disable:no-unused-variable */

import { TestBed, inject } from '@angular/core/testing';
import { HealthCenterService } from './health-center.service';

describe('Service: PostoSaude', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HealthCenterService]
    });
  });

  it('should ...', inject([HealthCenterService], (service: HealthCenterService) => {
    expect(service).toBeTruthy();
  }));
});
