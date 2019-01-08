import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntreprise } from 'app/shared/model/entreprise.model';
import { EntrepriseService } from './entreprise.service';

@Component({
    selector: 'jhi-entreprise-delete-dialog',
    templateUrl: './entreprise-delete-dialog.component.html'
})
export class EntrepriseDeleteDialogComponent {
    entreprise: IEntreprise;

    constructor(
        protected entrepriseService: EntrepriseService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.entrepriseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'entrepriseListModification',
                content: 'Deleted an entreprise'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entreprise-delete-popup',
    template: ''
})
export class EntrepriseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entreprise }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EntrepriseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.entreprise = entreprise;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
