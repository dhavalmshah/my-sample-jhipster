import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { CounterPartyDetailComponent } from 'app/entities/counter-party/counter-party-detail.component';
import { CounterParty } from 'app/shared/model/counter-party.model';

describe('Component Tests', () => {
  describe('CounterParty Management Detail Component', () => {
    let comp: CounterPartyDetailComponent;
    let fixture: ComponentFixture<CounterPartyDetailComponent>;
    const route = ({ data: of({ counterParty: new CounterParty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [CounterPartyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CounterPartyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CounterPartyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load counterParty on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.counterParty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
