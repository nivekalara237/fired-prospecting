import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { JhiLanguageHelper, User, UserService, AccountService, Account } from 'app/core';
import { Entreprise, IEntreprise } from '../../shared/model/entreprise.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import * as $ from 'jquery';
@Component({
    selector: 'jhi-user-mgmt-update',
    templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
    user: User;
    languages: any[];
    authorities: any[];
    isSaving: boolean;
    account: Account;
    entreprises: IEntreprise[];

    constructor(
        private languageHelper: JhiLanguageHelper,
        private userService: UserService,
        private route: ActivatedRoute,
        private accountService: AccountService,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ user }) => {
            this.user = user.body ? user.body : user;
        });
        this.authorities = [];
        this.userService.authorities().subscribe(authorities => {
            this.authorities = authorities;
        });
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });
        this.accountService.identity().then(account => {
            this.account = account;
            this.getEnterprise(account.id);
        });
        $(document).ready(function() {
            //console.log('JQUERY = ', $);
            //$('.selectpicker').selectpicker();
        });
    }

    getEnterprise(id: string) {
        this.userService.findEnterprise(id).subscribe(
            (res: HttpResponse<IEntreprise[]>) => {
                this.entreprises = res.body;
            },
            (res: HttpErrorResponse) => {
                console.log(res.message);
            }
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.user.id !== null) {
            if (!this.instanceOfEntreprise(this.user.entreprise)) {
                this.updateWithEnterperseAsString();
            } else {
                this.user.entrepriseId = this.user.entreprise.id;
                this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
            }
        } else {
            if (!this.instanceOfEntreprise(this.user.entreprise)) {
                this.saveWithEnterperseAsString();
            } else {
                this.user.entrepriseId = this.user.entreprise.id;
                this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
            }
        }
    }

    saveWithEnterperseAsString() {
        let eId = this.user.entreprise;
        this.user.entreprise = null;
        this.user.entrepriseId = eId;
        this.userService
            .createWithEnterpriseAsString(eId, this.user)
            .subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }
    updateWithEnterperseAsString() {
        let eId = this.user.entreprise;
        this.user.entreprise = null;
        this.user.entrepriseId = eId;
        this.userService
            .updateWithEnterpriseAsString(eId, this.user)
            .subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }

    instanceOfEntreprise(en: any): en is Entreprise {
        return 'designation' in en;
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
