import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFichier } from 'app/shared/model/fichier.model';
import { FichierService } from './fichier.service';

@Component({
    selector: 'jhi-fichier-delete-dialog',
    templateUrl: './fichier-delete-dialog.component.html'
})
export class FichierDeleteDialogComponent {
    fichier: IFichier;

    constructor(protected fichierService: FichierService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.fichierService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fichierListModification',
                content: 'Deleted an fichier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fichier-delete-popup',
    template: ''
})
export class FichierDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fichier }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FichierDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.fichier = fichier;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fichier', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fichier', { outlets: { popup: null } }]);
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
