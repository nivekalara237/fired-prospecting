import {
    Component,
    OnInit,
    AfterViewInit,
    ChangeDetectorRef,
    AfterContentInit,
    AfterContentChecked,
    AfterViewChecked,
    DoCheck
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IUser, UserService } from 'app/core';
import { AccountService, Account } from 'app/core';
import { NgbDatepickerConfig, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateMomentAdapter } from 'app/shared';

import { IProspect } from 'app/shared/model/prospect.model';
import { CompteRenduSuivi, ICompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';
import { CompteRenduSuiviService } from 'app/entities/compte-rendu-suivi/compte-rendu-suivi.service';
import { AmazingTimePickerService } from 'amazing-time-picker';
import { NgbDateFRParserFormatter } from '../../shared/util/ngb-date-fr-parser-formatter';
import * as moment from 'moment';

@Component({
    selector: 'jhi-prospect-detail',
    templateUrl: './prospect-detail.component.html',
    styleUrls: ['./prospect.component.css'],
    providers: [{ provide: NgbDateParserFormatter, useClass: NgbDateFRParserFormatter }]
})
export class ProspectDetailComponent implements OnInit, AfterViewInit, AfterContentInit, AfterContentChecked, DoCheck, AfterViewChecked {
    compteRenduSuivi: ICompteRenduSuivi = { dateProchaineRdv: '', contenu: '' };
    prospect: IProspect;
    isSaving: boolean;
    account: Account;
    hourStep: string;
    compteRendu: string;
    dateRdv: string;
    formGroup: FormGroup;
    compteRendus: ICompteRenduSuivi[];
    contenu: string;
    dateProchainRdv: string;
    eventSubscriber: Subscription;
    public loading = false;
    public selectedTime: string;
    constructor(
        protected cdref: ChangeDetectorRef,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService,
        protected compteRenduService: CompteRenduSuiviService,
        private atp: AmazingTimePickerService
    ) {}

    ngAfterViewInit() {
        // or ngOnInit or whatever
        setTimeout(() => {
            this.bdcallout();
        });
        this.cdref.detectChanges();
    }

    ngDoCheck() {
        //console.log('Trace doCheck');
    }

    ngAfterContentChecked() {
        //console.log('Trace after contente checked');
        this.bdcallout();
    }
    ngAfterViewChecked() {
        //console.log('Trace after view checked');
        this.bdcallout();
    }

    ngAfterContentInit() {
        this.cdref.detectChanges();
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prospect }) => {
            this.prospect = prospect;
            this.getAll();
        });
        this.compteRenduSuivi = new CompteRenduSuivi();
        this.activatedRoute.data.subscribe(({ compteRenduSuivi }) => {
            this.compteRenduSuivi = compteRenduSuivi;
        });

        this.registerChangeInProspects();

        this.formGroup = new FormGroup({
            DateRdv: new FormControl('', [
                //Validators.required,
                Validators.minLength(10),
                Validators.maxLength(12)
            ]),
            HourStep: new FormControl('', [
                //Validators.required,
            ]),
            Contenu: new FormControl('', [
                Validators.required,
                Validators.minLength(5)
                //Validators.maxLength(64)
            ])
        });
        this.bdcallout();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProspects() {
        this.eventSubscriber = this.eventManager.subscribe('compteRenduSuiviListModification', response => this.getAll());
    }

    previousState() {
        window.history.back();
    }

    bdcallout() {
        var items = Array('bd-callout bd-callout-primary', 'bd-callout bd-callout-danger', 'bd-callout bd-callout-warning');
        return items[Math.floor(Math.random() * items.length)];
    }

    getAll() {
        this.loading = true; /*
        this.compteRenduService.query().subscribe(
            (res: HttpResponse<ICompteRenduSuivi[]>) => {
                //this.compteRendus = res.body;
                //console.log(res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );*/

        this.compteRenduService.findByProspect(this.prospect.id).subscribe(
            (res: HttpResponse<ICompteRenduSuivi[]>) => {
                this.compteRendus = res.body;
                this.loading = false;
                console.log('CRSUIVI[]=', res.body);
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
                this.loading = false;
            }
        );
    }

    saveCRS() {
        this.loading = true;
        this.isSaving = true;

        let date = moment(this.dateProchainRdv).format('YYYY-MM-DD') + ' ' + this.selectedTime;
        this.compteRenduSuivi = { dateProchaineRdv: date, prospectId: this.prospect.id, contenu: this.contenu };
        this.subscribeToSaveResponse(this.compteRenduService.createInProspectPage(this.compteRenduSuivi));
        //}
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompteRenduSuivi>>) {
        result.subscribe(
            (res: HttpResponse<ICompteRenduSuivi>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.loading = false;
        this.previousState();
    }

    protected onSaveError() {
        //alert("lll");
        this.loading = false;
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    isEmpty(str: string) {
        if (str === undefined) return true;
        else return str === '';
    }

    openTimeModal() {
        const amazingTimePicker = this.atp.open();
        amazingTimePicker.afterClose().subscribe(time => {
            this.selectedTime = time;
        });
    }
}
