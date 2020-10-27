import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { PackingDetailComponent } from 'app/entities/packing/packing-detail.component';
import { Packing } from 'app/shared/model/packing.model';

describe('Component Tests', () => {
  describe('Packing Management Detail Component', () => {
    let comp: PackingDetailComponent;
    let fixture: ComponentFixture<PackingDetailComponent>;
    const route = ({ data: of({ packing: new Packing(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [PackingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PackingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PackingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load packing on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.packing).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
