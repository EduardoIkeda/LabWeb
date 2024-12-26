import { TestBed } from '@angular/core/testing';

import { ConsultasReportService } from './consultas-report.service';

describe('ConsultasReportService', () => {
  let service: ConsultasReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConsultasReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
