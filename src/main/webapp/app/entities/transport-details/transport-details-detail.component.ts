import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransportDetails } from 'app/shared/model/transport-details.model';

@Component({
  selector: 'jhi-transport-details-detail',
  templateUrl: './transport-details-detail.component.html',
})
export class TransportDetailsDetailComponent implements OnInit {
  transportDetails: ITransportDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transportDetails }) => (this.transportDetails = transportDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
