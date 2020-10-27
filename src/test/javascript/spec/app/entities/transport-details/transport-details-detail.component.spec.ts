import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { TransportDetailsDetailComponent } from 'app/entities/transport-details/transport-details-detail.component';
import { TransportDetails } from 'app/shared/model/transport-details.model';

describe('Component Tests', () => {
  describe('TransportDetails Management Detail Component', () => {
    let comp: TransportDetailsDetailComponent;
    let fixture: ComponentFixture<TransportDetailsDetailComponent>;
    const route = ({ data: of({ transportDetails: new TransportDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [TransportDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TransportDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransportDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transportDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transportDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
