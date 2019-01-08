import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from './channel.service';
import { IEntreprise } from 'app/shared/model/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-channel-update',
    templateUrl: './channel-update.component.html'
})
export class ChannelUpdateComponent implements OnInit {
    channel: IChannel;
    isSaving: boolean;

    entreprises: IEntreprise[];

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected channelService: ChannelService,
        protected entrepriseService: EntrepriseService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ channel }) => {
            this.channel = channel;
        });
        this.entrepriseService.query().subscribe(
            (res: HttpResponse<IEntreprise[]>) => {
                this.entreprises = res.body;
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
        if (this.channel.id !== undefined) {
            this.subscribeToSaveResponse(this.channelService.update(this.channel));
        } else {
            this.subscribeToSaveResponse(this.channelService.create(this.channel));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IChannel>>) {
        result.subscribe((res: HttpResponse<IChannel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEntrepriseById(index: number, item: IEntreprise) {
        return item.id;
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
