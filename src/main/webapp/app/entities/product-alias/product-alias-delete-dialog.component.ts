import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAlias } from 'app/shared/model/product-alias.model';
import { ProductAliasService } from './product-alias.service';

@Component({
  templateUrl: './product-alias-delete-dialog.component.html',
})
export class ProductAliasDeleteDialogComponent {
  productAlias?: IProductAlias;

  constructor(
    protected productAliasService: ProductAliasService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productAliasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productAliasListModification');
      this.activeModal.close();
    });
  }
}
