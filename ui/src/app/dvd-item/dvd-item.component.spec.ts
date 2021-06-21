import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DvdItemComponent } from './dvd-item.component';

describe('DvdItemComponent', () => {
  let component: DvdItemComponent;
  let fixture: ComponentFixture<DvdItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DvdItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DvdItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
