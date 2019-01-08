import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';
import { CompteRenduSuiviService } from './compte-rendu-suivi.service';
import { ISuivi } from 'app/shared/model/suivi.model';
import { SuiviService } from 'app/entities/suivi';

@Component({
    selector: 'jhi-compte-rendu-suivi-update',
    templateUrl: './compte-rendu-suivi-update.component.html'
})
export class CompteRenduSuiviUpdateComponent implements OnInit {
    compteRenduSuivi: ICompteRenduSuivi;
    isSaving: boolean;

    suivis: ISuivi[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected compteRenduSuiviService: CompteRenduSuiviService,
        protected suiviService: SuiviService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compteRenduSuivi }) => {
            this.compteRenduSuivi = compteRenduSuivi;
        });
        this.suiviService.query().subscribe(
            (res: HttpResponse<ISuivi[]>) => {
                this.suivis = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.compteRenduSuivi.id !== undefined) {
            this.subscribeToSaveResponse(this.compteRenduSuiviService.update(this.compteRenduSuivi));
        } else {
            this.subscribeToSaveResponse(this.compteRenduSuiviService.create(this.compteRenduSuivi));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompteRenduSuivi>>) {
        result.subscribe((res: HttpResponse<ICompteRenduSuivi>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSuiviById(index: number, item: ISuivi) {
        return item.id;
    }
}
