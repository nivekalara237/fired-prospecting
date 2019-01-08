import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IObjet } from 'app/shared/model/objet.model';
import { ObjetService } from './objet.service';
import { IRapport } from 'app/shared/model/rapport.model';
import { RapportService } from 'app/entities/rapport';

@Component({
    selector: 'jhi-objet-update',
    templateUrl: './objet-update.component.html'
})
export class ObjetUpdateComponent implements OnInit {
    objet: IObjet;
    isSaving: boolean;

    rapports: IRapport[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected objetService: ObjetService,
        protected rapportService: RapportService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ objet }) => {
            this.objet = objet;
        });
        this.rapportService.query().subscribe(
            (res: HttpResponse<IRapport[]>) => {
                this.rapports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.objet.id !== undefined) {
            this.subscribeToSaveResponse(this.objetService.update(this.objet));
        } else {
            this.subscribeToSaveResponse(this.objetService.create(this.objet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IObjet>>) {
        result.subscribe((res: HttpResponse<IObjet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRapportById(index: number, item: IRapport) {
        return item.id;
    }
}
