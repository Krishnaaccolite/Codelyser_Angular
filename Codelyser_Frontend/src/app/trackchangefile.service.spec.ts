import { TestBed } from '@angular/core/testing';

import { TrackchangefileService } from './trackchangefile.service';

describe('TrackchangefileService', () => {
  let service: TrackchangefileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrackchangefileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
