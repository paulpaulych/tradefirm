import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesPointComponent } from './sales-points.component';

describe('SalesPointsComponent', () => {
  let component: SalesPointsComponent;
  let fixture: ComponentFixture<SalesPointsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SalesPointsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesPointsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
