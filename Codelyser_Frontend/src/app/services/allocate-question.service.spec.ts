import { TestBed } from '@angular/core/testing';

import { AllocateQuestionService } from './allocate-question.service';

describe('AllocateQuestionService', () => {
  let service: AllocateQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AllocateQuestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
