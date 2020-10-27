import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICounterParty } from 'app/shared/model/counter-party.model';

@Component({
  selector: 'jhi-counter-party-detail',
  templateUrl: './counter-party-detail.component.html',
})
export class CounterPartyDetailComponent implements OnInit {
  counterParty: ICounterParty | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ counterParty }) => (this.counterParty = counterParty));
  }

  previousState(): void {
    window.history.back();
  }
}
