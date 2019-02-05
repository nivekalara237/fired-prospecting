import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRapport } from 'app/shared/model/rapport.model';
import { RapportService } from './rapport.service';
import { IUser, UserService } from 'app/core';
import { AccountService, Account } from 'app/core';

@Component({
    selector: 'jhi-rapport-update',
    templateUrl: './rapport-update.component.html'
})
export class RapportUpdateComponent implements OnInit {
    rapport: IRapport;
    isSaving: boolean;
    account: Account;
    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rapportService: RapportService,
        protected userService: UserService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rapport }) => {
            this.rapport = rapport;
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
        if (this.rapport.id !== undefined) {
            this.subscribeToSaveResponse(this.rapportService.update(this.rapport));
        } else {
            this.subscribeToSaveResponse(this.rapportService.create(this.rapport));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRapport>>) {
        result.subscribe((res: HttpResponse<IRapport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
