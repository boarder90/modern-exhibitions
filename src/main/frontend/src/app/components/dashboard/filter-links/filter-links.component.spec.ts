import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterLinksComponent } from './filter-links.component';

describe('FilterLinksComponent', () => {
  let component: FilterLinksComponent;
  let fixture: ComponentFixture<FilterLinksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilterLinksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterLinksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
