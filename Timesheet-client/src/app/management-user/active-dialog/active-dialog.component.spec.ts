import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveDialogComponent } from './active-dialog.component';

describe('ActiveDialogComponent', () => {
  let component: ActiveDialogComponent;
  let fixture: ComponentFixture<ActiveDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActiveDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActiveDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
