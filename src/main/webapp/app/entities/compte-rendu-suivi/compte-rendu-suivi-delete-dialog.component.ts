import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';
import { CompteRenduSuiviService } from './compte-rendu-suivi.service';

@Component({
    selector: 'jhi-compte-rendu-suivi-delete-dialog',
    templateUrl: './compte-rendu-suivi-delete-dialog.component.html'
})
export class CompteRenduSuiviDeleteDialogComponent {
    compteRenduSuivi: ICompteRenduSuivi;

    constructor(
        protected compteRenduSuiviService: CompteRenduSuiviService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.compteRenduSuiviService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'compteRenduSuiviListModification',
                content: 'Deleted an compteRenduSuivi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-compte-rendu-suivi-delete-popup',
    template: ''
})
export class CompteRenduSuiviDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compteRenduSuivi }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompteRenduSuiviDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.compteRenduSuivi = compteRenduSuivi;
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
