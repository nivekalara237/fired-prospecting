import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { FireDSharedModule } from 'app/shared';
import {
    FichierComponent,
    FichierDetailComponent,
    FichierUpdateComponent,
    FichierDeletePopupComponent,
    FichierDeleteDialogComponent,
    fichierRoute,
    fichierPopupRoute
} from './';

const ENTITY_STATES = [...fichierRoute, ...fichierPopupRoute];

@NgModule({
    imports: [FireDSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FichierComponent,
        FichierDetailComponent,
        FichierUpdateComponent,
        FichierDeleteDialogComponent,
        FichierDeletePopupComponent
    ],
    entryComponents: [FichierComponent, FichierUpdateComponent, FichierDeleteDialogComponent, FichierDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDFichierModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
