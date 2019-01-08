import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProspect } from 'app/shared/model/prospect.model';
import { ProspectService } from './prospect.service';
import { ISuivi } from 'app/shared/model/suivi.model';
import { SuiviService } from 'app/entities/suivi';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-prospect-update',
    templateUrl: './prospect-update.component.html'
})
export class ProspectUpdateComponent implements OnInit {
    prospect: IProspect;
    isSaving: boolean;

    suivis: ISuivi[];

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected prospectService: ProspectService,
        protected suiviService: SuiviService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prospect }) => {
            this.prospect = prospect;
        });
        this.suiviService.query({ filter: 'prospect-is-null' }).subscribe(
            (res: HttpResponse<ISuivi[]>) => {
                if (!this.prospect.suivi || !this.prospect.suivi.id) {
                    this.suivis = res.body;
                } else {
                    this.suiviService.find(this.prospect.suivi.id).subscribe(
                        (subRes: HttpResponse<ISuivi>) => {
                            this.suivis = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.prospect.id !== undefined) {
            this.subscribeToSaveResponse(this.prospectService.update(this.prospect));
        } else {
            this.subscribeToSaveResponse(this.prospectService.create(this.prospect));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProspect>>) {
        result.subscribe((res: HttpResponse<IProspect>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
