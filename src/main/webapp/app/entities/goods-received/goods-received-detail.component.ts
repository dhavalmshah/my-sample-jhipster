import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoodsReceived } from 'app/shared/model/goods-received.model';

@Component({
  selector: 'jhi-goods-received-detail',
  templateUrl: './goods-received-detail.component.html',
})
export class GoodsReceivedDetailComponent implements OnInit {
  goodsReceived: IGoodsReceived | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goodsReceived }) => (this.goodsReceived = goodsReceived));
  }

  previousState(): void {
    window.history.back();
  }
}
