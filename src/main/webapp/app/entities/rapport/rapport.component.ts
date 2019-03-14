import { Component, OnInit, OnDestroy, ChangeDetectorRef, Directive, EventEmitter, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { FormBuilder, FormControl, FormGroup, Validators, NgForm } from '@angular/forms';

import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import * as $ from 'jquery';
import { IRapport } from 'app/shared/model/rapport.model';
import { AccountService, UserService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RapportService } from './rapport.service';
import { IUser } from '../../core/user/user.model';
import * as moment from 'moment';
import { IObjet } from '../../shared/model/objet.model';

function readBase64(file): Promise<any> {
    var reader = new FileReader();
    var future = new Promise((resolve, reject) => {
        reader.addEventListener(
            'load',
            function() {
                resolve(reader.result);
            },
            false
        );

        reader.addEventListener(
            'error',
            function(event) {
                reject(event);
            },
            false
        );

        reader.readAsDataURL(file);
    });
    return future;
}

@Component({
    selector: 'jhi-rapport',
    templateUrl: './rapport.component.html',
    styleUrls: ['./rapport.component.css', './rapport-file-input.css']
})
export class RapportComponent implements OnInit, OnDestroy {
    acceptExt: string =
        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel,.png,.jpg,.jpeg,.apk,.exe,.pdf,.psd,.doc,.docx,.ppt,.zip,.rar,.txt,.xlsx,.xls,.csv,.mp4,.mp3';
    currentAccount: any;
    rapports: IRapport[];
    rapportsChecked: IRapport[];
    rapportSelected: IRapport;
    newrapport: IRapport = { contenu: '', objet: '', copies: '', userId: '', haveFile: false };
    objet: string;
    copie: string;
    contenu: string;
    isSaving: boolean = false;
    files: FileReader;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    commercials: IUser[];
    lastRapportSaved: string;
    compose: boolean = false;
    displayDetails: boolean = false;
    displayList: boolean = true;
    FILES: File[];
    filesCurrentRapport: IObjet[];
    formData: FormData = new FormData();
    composeForm: FormGroup;
    composeSubmitted = false;
    commercialSelected: IUser;
    @ViewChild('editForm') mytemplateForm: NgForm;

    constructor(
        protected rapportService: RapportService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        private cd: ChangeDetectorRef,
        private fb: FormBuilder
    ) {
        this.itemsPerPage = 10; //ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }
    ngOnInit() {
        this.composeForm = new FormGroup({
            Contenu: new FormControl('', [Validators.required]),
            Objet: new FormControl('', [Validators.required])
        });

        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.loadComm();
        });
        this.activatedRoute.data.subscribe(({ rapport }) => {
            this.newrapport = rapport;
        });
        this.registerChangeInRapports();
        $(document).ready(function() {
            //console.log('JQUERY = ', $);
        });
        this.formData = new FormData();
    }

    loadComm() {
        //console.log("ENTREPRISE",this.currentAccount);
        this.userService.findComm(this.currentAccount.entrepriseId).subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.commercials = res.body;
                if (this.commercials.length !== 0) {
                    this.commercialSelected = this.commercials[0];
                    //console.log('USER = ', res.body);
                    this.loadAll();
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    _compose() {
        this.compose = true;
        this.displayDetails = false;
        this.displayList = false;
    }

    _displayList(all: boolean, comm: IUser) {
        this.compose = false;
        this.displayDetails = false;
        this.displayList = true;
        this.commercialSelected = comm;
        this.loadAll();
    }
    _displayDetails(rapport: IRapport) {
        //this.rapportSelected = rapport;
        this.loadRapports(rapport.id);
        this.compose = false;
        this.displayDetails = true;
        this.displayList = false;
    }

    download(filename: string) {
        this.rapportService.downloadFile(filename).subscribe(
            (res: HttpResponse<any>) => {
                console.log('FILE_RESPONSE', res.body);
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
    }

    fileConvertSize(aSize: number) {
        aSize = Math.abs(aSize);
        let def: Array<[number, string]> = [
            [1, 'octets'],
            [1024, 'ko'],
            [1024 * 1024, 'Mo'],
            [1024 * 1024 * 1024, 'Go'],
            [1024 * 1024 * 1024 * 1024, 'To']
        ];
        for (let i = 0; i < def.length; i++) {
            if (aSize < def[i][0]) return (aSize / def[i - 1][0]).toFixed(2) + ' ' + def[i - 1][1];
        }
    }

    backToList() {
        this.compose = false;
        this.displayDetails = false;
        this.displayList = true;
        this.loadAll();
    }

    saveOnlyRepport() {
        this.isSaving = false;
        let hfile = this.FILES !== undefined && this.FILES.length != 0;
        this.newrapport = {
            contenu: this.contenu,
            copies: this.copie,
            objet: this.objet,
            userId: this.currentAccount.id,
            haveFile: hfile
        };
        //console.log("RAPPORT",this.newrapport);
        this.rapportService.create(this.newrapport).subscribe(
            (res: HttpResponse<IRapport>) => {
                //saveFileOfRepport(res.body);
                if (this.FILES.length !== 0) {
                    this.isSaving = true;
                    this.saveFileOfRepport(res.body.id);
                } else {
                    this.mytemplateForm.reset();
                    this.isSaving = true;
                }
            },
            (res: HttpErrorResponse) => {
                this.isSaving = true;
                this.onError(res.message);
            }
        );
    }

    loadRapports(id: string) {
        this.rapportService.getDetails(id).subscribe(
            (res: HttpResponse<any>) => {
                this.rapportSelected = res.body.rapport;
                this.files = res.body.files;
                console.log('MAP', res.body);
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
    }

    saveFileOfRepport(r: string) {
        let form = new FormData();
        //form.append("files",this.FILES[0]);
        for (let i = 0; i < this.FILES.length; i++) {
            form.append('files', this.FILES[i]);
        }

        //console.log("FILE",form.hasOwnProperty("d"));
        this.rapportService.createWithFile(r, form).subscribe(
            (res: HttpResponse<any>) => {
                //console.log('RES',res.body)
                this.isSaving = true;
                this.mytemplateForm.reset();
            },
            (res: HttpErrorResponse) => {
                console.log(res.message);
                this.isSaving = true;
                this.mytemplateForm.reset();
                this.onError(res.message);
            }
        );
        return false;
    }

    loadAll() {
        if (this.currentSearch) {
            this.rapportService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IRapport[]>) => this.paginateRapports(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.rapportService
            .findByUser(
                {
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                },
                this.commercialSelected.id
            )
            .subscribe(
                (res: HttpResponse<IRapport[]>) => this.paginateRapports(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/rapport'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/rapport',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/rapport',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRapport) {
        return item.id;
    }

    registerChangeInRapports() {
        this.eventSubscriber = this.eventManager.subscribe('rapportListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateRapports(data: IRapport[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.rapports = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    onFileChange(event) {
        var filenames = $('.filenames');
        if (event.target.files && event.target.files.length) {
            this.FILES = event.target.files;
            for (let i = 0; i < event.target.files.length; i++) {
                this.formData.append('fileArray', event.target.files[i], event.target.files[i].name);
                //this.FILES[i] = event.target.files[i];
            }

            filenames.html('');
            var fileName = '';
            //var fd= new FormData();
            $.each(event.target.files, function(i, f) {
                //this.formData.append("fileArray",f);
                console.log('TYPE', f.type);
                if (f.type.match('image.*')) {
                    var reader = new FileReader();
                    reader.onload = (function(theFile) {
                        return function(e) {
                            // Render thumbnail.
                            var span = document.createElement('span');
                            span.innerHTML = [
                                '<img style="width:75px;margin-right:5px" src="',
                                e.target.result,
                                '" title="',
                                escape(theFile.name),
                                '"/>'
                            ].join('');
                            filenames.append(span);
                        };
                    })(f);

                    // Read in the image file as a data URL.
                    reader.readAsDataURL(f);
                } else if (f.type.indexOf('pdf')) {
                    var span = document.createElement('span');
                    span.innerHTML = ['<img style="width:75px;margin-right:5px" src="', 'assets/file.png', '" title="', 'xxxx', '"/>'].join(
                        ''
                    );
                    filenames.append(span);
                }
                console.log(f);

                readBase64(f).then(function(data) {
                    //console.log(data);
                });
                //filenames.append("<span>"+ f.name +"</span><br>");
            });
        }
    }

    checked(rapport: IRapport) {
        for (let i = 0; i < this.rapports.length; i++) {
            if (this.rapports[i].id === rapport.id) {
                if (this.rapports[i].checked) this.rapports[i].checked = false;
                else this.rapports[i].checked = true;
            }
        }
    }
    checkedAll(event) {
        for (let i = 0; i < this.rapports.length; i++) {
            this.rapports[i].checked = true;
        }
    }

    isToday(date: string) {
        return moment(date).isSame(moment(), 'day');
    }

    trimText(text: string) {
        if (text.length > 64) return text.substring(0, 64) + '...';
        else return text;
    }
}
