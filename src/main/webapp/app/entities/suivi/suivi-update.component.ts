import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISuivi } from 'app/shared/model/suivi.model';
import { SuiviService } from './suivi.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-suivi-update',
    templateUrl: './suivi-update.component.html'
})
export class SuiviUpdateComponent implements OnInit {
    suivi: ISuivi;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected suiviService: SuiviService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ suivi }) => {
            this.suivi = suivi;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
