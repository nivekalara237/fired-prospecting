import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICopie } from 'app/shared/model/copie.model';
import { CopieService } from './copie.service';
import { IRapport } from 'app/shared/model/rapport.model';
import { RapportService } from 'app/entities/rapport';

@Component({
    selector: 'jhi-copie-update',
    templateUrl: './copie-update.component.html'
})
export class CopieUpdateComponent implements OnInit {
    copie: ICopie;
    isSaving: boolean;

    rapports: IRapport[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected copieService: CopieService,
        protected rapportService: RapportService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ copie }) => {
            this.copie = copie;
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
        if (this.copie.id !== undefined) {
            this.subscribeToSaveResponse(this.copieService.update(this.copie));
        } else {
            this.subscribeToSaveResponse(this.copieService.create(this.copie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICopie>>) {
        result.subscribe((res: HttpResponse<ICopie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
