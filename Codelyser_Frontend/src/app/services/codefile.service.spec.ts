import { TestBed } from '@angular/core/testing';

import { CodefileService } from './codefile.service';

describe('CodefileService', () => {
  let service: CodefileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodefileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
