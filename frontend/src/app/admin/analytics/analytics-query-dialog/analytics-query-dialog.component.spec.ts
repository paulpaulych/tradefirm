import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyticsQueryDialogComponent } from './analytics-query-dialog.component';

describe('AnalyticsQueryDialogComponent', () => {
  let component: AnalyticsQueryDialogComponent;
  let fixture: ComponentFixture<AnalyticsQueryDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalyticsQueryDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalyticsQueryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
