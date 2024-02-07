import { TestBed } from '@angular/core/testing';

import { CandidatedataService } from './candidatedata.service';

describe('CandidatedataService', () => {
  let service: CandidatedataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CandidatedataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
