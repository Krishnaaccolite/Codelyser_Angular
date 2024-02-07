import { TestBed } from '@angular/core/testing';

import { CandidatefileexplorerService } from './candidatefileexplorer.service';

describe('CandidatefileexplorerService', () => {
  let service: CandidatefileexplorerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CandidatefileexplorerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});