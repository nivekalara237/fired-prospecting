import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { IProspect } from 'app/shared/model/prospect.model';
import { ProspectService } from './prospect.service';
import { ISuivi } from 'app/shared/model/suivi.model';
import { SuiviService } from 'app/entities/suivi';
import { IUser, UserService } from 'app/core';
import { AccountService, Account } from 'app/core';

@Component({
    selector: 'jhi-prospect-update',
    templateUrl: './prospect-update.component.html'
})
export class ProspectUpdateComponent implements OnInit {
    prospect: IProspect;
    isSaving: boolean;
    account: Account;
    suivis: ISuivi[];
    hourStep: string = '00';
    minuteStep: string = '00';
    compteRendu: string;
    dateRdv: string;
    users: IUser[];
    formGroup: FormGroup;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected prospectService: ProspectService,
        protected suiviService: SuiviService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected eventManager: JhiEventManager,
        protected translate: TranslateService
    ) // protected formGroup: FormGroup
    {}

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

        this.accountService.identity().then(account => {
            this.account = account;
            //window.ACC = account;
            //console.log("Account {} ",account);
            //console.log("HHHH {} ",account["id"]);
        });
        this.registerAuthenticationSuccess();

        this.formGroup = new FormGroup({
            Email: new FormControl('', [
                //Validators.required,
                Validators.email,
                Validators.pattern(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)
            ]),
            Name: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(64)]),
            Phone: new FormControl('', [
                //Validators.required,
                Validators.minLength(6),
                Validators.maxLength(64)
            ]),
            DateRdv: new FormControl('', [
                //Validators.required,
                Validators.minLength(10),
                Validators.maxLength(12)
            ]),
            HourStep: new FormControl('0', [
                //Validators.required,
            ]),
            MinuteStep: new FormControl('0', [
                //Validators.required,
            ]),
            CompteRendu: new FormControl('', [
                Validators.required,
                Validators.minLength(5)
                //Validators.maxLength(64)
            ])
        });
    }

    onSubmit() {
        console.log(this.formGroup);
    }

    onReset() {
        this.formGroup.reset();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            //console.log("Account3 {} ",message);
            this.accountService.identity().then(account => {
                this.account = account;
                //console.log("Account2 {} ",account);
            });
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = false;

        if (this.isEmpty(this.prospect.telephone) && this.isEmpty(this.prospect.email)) {
            //this.isSaving = false;
            //this.onError(this.jhiTranslate.fireD.prospect.emailOrPhoneRequired);
            //this.onError(this.translate.get());
        } else {
            this.prospect.user = this.account;
            //console.log("DATE {} ",this.prospect.dateRdv.format("YYYY/DD/MM"));
            if (typeof this.prospect.dateRdv !== 'string') {
                //this.prospect.dateRdv = this.prospect.dateRdv.format("YYYY/MM/DD")+" "+this.parseHour();
            }

            this.prospect.userId = this.account['id'];
            //console.log("PROSPECT {} ",this.prospect);

            if (this.prospect.id !== undefined) {
                this.subscribeToSaveResponse(this.prospectService.update(this.prospect));
            } else {
                this.subscribeToSaveResponse(this.prospectService.create(this.prospect));
            }
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

    isEmpty(str: string) {
        if (str === undefined) return true;
        else return str === '';
    }

    extractDate() {
        if (this.isEmpty(this.prospect.dateRdv)) return null;
        else {
            //console.log("RDV",this.prospect.dateRdv.format("YYYY/DD/MM"));
            return this.prospect.dateRdv;
        }
    }

    parseHour(): string {
        let hour = parseInt(this.hourStep) < 10 ? '0' + this.hourStep : this.hourStep;
        let minute = parseInt(this.minuteStep) < 10 ? '0' + this.minuteStep : this.minuteStep;
        return hour + ':' + minute;
    }
}
