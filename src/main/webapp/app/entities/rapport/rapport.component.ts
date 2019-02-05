import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import * as $ from 'jquery';
import { IRapport } from 'app/shared/model/rapport.model';
import { AccountService, UserService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RapportService } from './rapport.service';
import { IUser } from '../../core/user/user.model';

@Component({
    selector: 'jhi-rapport',
    templateUrl: './rapport.component.html',
    styleUrls: ['./rapport.component.css', './rapport-file-input.css']
})
export class RapportComponent implements OnInit, OnDestroy {
    currentAccount: any;
    rapports: IRapport[];
    newrapport: IRapport;
    objet: string;
    copie: string;
    contenu: string;
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

    compose: boolean = false;
    displayDetails: boolean = false;
    displayList: boolean = true;

    constructor(
        protected rapportService: RapportService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        private cd: ChangeDetectorRef
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
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

    loadComm() {
        this.userService.findComm('niveka-dev-team').subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.commercials = res.body;
                console.log('USER = ', res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    _compose() {
        this.compose = true;
        this.displayDetails = false;
        this.displayList = false;
        this.loadFileInput();
    }

    _displayList(all: boolean) {
        this.compose = false;
        this.displayDetails = false;
        this.displayList = true;
    }
    _displayDetails() {
        this.compose = false;
        this.displayDetails = false;
        this.displayList = true;
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
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
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

    ngOnInit() {
        this.loadAll();
        this.loadFileInput();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.activatedRoute.data.subscribe(({ rapport }) => {
            this.newrapport = rapport;
        });
        this.registerChangeInRapports();

        $(document).ready(function() {
            console.log('JQUERY = ', $);
        });

        this.loadComm();
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
        gi;

        if (event.target.files && event.target.files.length) {
            const [file] = event.target.files;
        }
    }

    loadFileInput() {
        'use strict';
        (function(document, window, index) {
            var inputs = document.querySelectorAll('.input-file');
            Array.prototype.forEach.call(inputs, function(input) {
                var label = input.nextElementSibling,
                    labelVal = label.innerHTML;
                var filenames = $('.filenames');

                input.addEventListener('change', function(e) {
                    filenames.html('');
                    var fileName = '';
                    $.each(this.files, function(i, f) {
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
                        }

                        //filenames.append("<span>"+ f.name +"</span><br>");
                    });
                    if (this.files && this.files.length > 1)
                        fileName = (this.getAttribute('data-multiple-caption') || '').replace('{count}', this.files.length);
                    else fileName = e.target.value.split('\\').pop();

                    if (fileName) label.querySelector('span').innerHTML = fileName;
                    else label.innerHTML = labelVal;
                });

                // Firefox bug fix
                input.addEventListener('focus', function() {
                    input.classList.add('has-focus');
                });
                input.addEventListener('blur', function() {
                    input.classList.remove('has-focus');
                });
            });
        })(document, window, 0);
    }
}
