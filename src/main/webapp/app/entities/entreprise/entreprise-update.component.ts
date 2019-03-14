import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEntreprise } from 'app/shared/model/entreprise.model';
import { EntrepriseService } from './entreprise.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-entreprise-update',
    templateUrl: './entreprise-update.component.html'
})
export class EntrepriseUpdateComponent implements OnInit {
    entreprise: IEntreprise;
    isSaving: boolean;

    users: IUser[];
    rangeU: string[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected entrepriseService: EntrepriseService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ entreprise }) => {
            this.entreprise = entreprise;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.entrepriseService.getRangeUser().subscribe(
            (res: HttpResponse<string[]>) => {
                this.rangeU = res.body;
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.entreprise.id !== undefined) {
            this.subscribeToSaveResponse(this.entrepriseService.update(this.entreprise));
        } else {
            this.subscribeToSaveResponse(this.entrepriseService.create(this.entreprise));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntreprise>>) {
        result.subscribe((res: HttpResponse<IEntreprise>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
