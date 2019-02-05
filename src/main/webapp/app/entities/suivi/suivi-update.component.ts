import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISuivi } from 'app/shared/model/suivi.model';
import { SuiviService } from './suivi.service';

@Component({
    selector: 'jhi-suivi-update',
    templateUrl: './suivi-update.component.html'
})
export class SuiviUpdateComponent implements OnInit {
    suivi: ISuivi;
    isSaving: boolean;

    constructor(protected suiviService: SuiviService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ suivi }) => {
            this.suivi = suivi;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.suivi.id !== undefined) {
            this.subscribeToSaveResponse(this.suiviService.update(this.suivi));
        } else {
            this.subscribeToSaveResponse(this.suiviService.create(this.suivi));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuivi>>) {
        result.subscribe((res: HttpResponse<ISuivi>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
