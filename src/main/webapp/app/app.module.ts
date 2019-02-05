import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { FireDSharedModule } from 'app/shared';
import { FireDCoreModule } from 'app/core';
import { FireDAppRoutingModule } from './app-routing.module';
import { FireDHomeModule } from './home/home.module';
import { FireDAccountModule } from './account/account.module';
import { FireDEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// import { MaterialModule } from './material.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
// import {MaterialModule} from "./material.module";
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { NbThemeModule } from '@nebular/theme';
import { NgxLoadingModule } from 'ngx-loading';

import { EditorModule } from '@tinymce/tinymce-angular';
@NgModule({
    imports: [
        BrowserModule,
        FireDAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NbThemeModule.forRoot(),
        NgxUiLoaderModule,
        NgbModule,
        FormsModule,
        ReactiveFormsModule,
        NgBootstrapFormValidationModule.forRoot(),
        NgxLoadingModule.forRoot({}),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            alertTimeout: 25000,
            i18nEnabled: true,
            defaultI18nLang: 'fr'
        }),
        FireDSharedModule.forRoot(),
        FireDCoreModule,
        FireDHomeModule,
        FireDAccountModule,
        EditorModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        FireDEntityModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class FireDAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
