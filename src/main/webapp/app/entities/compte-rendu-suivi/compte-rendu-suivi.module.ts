import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import {
    CompteRenduSuiviComponent,
    CompteRenduSuiviDetailComponent,
    CompteRenduSuiviUpdateComponent,
    CompteRenduSuiviDeletePopupComponent,
    CompteRenduSuiviDeleteDialogComponent,
    compteRenduSuiviRoute,
    compteRenduSuiviPopupRoute
} from './';

const ENTITY_STATES = [...compteRenduSuiviRoute, ...compteRenduSuiviPopupRoute];

@NgModule({
    imports: [FireDSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompteRenduSuiviComponent,
        CompteRenduSuiviDetailComponent,
        CompteRenduSuiviUpdateComponent,
        CompteRenduSuiviDeleteDialogComponent,
        CompteRenduSuiviDeletePopupComponent
    ],
    entryComponents: [
        CompteRenduSuiviComponent,
        CompteRenduSuiviUpdateComponent,
        CompteRenduSuiviDeleteDialogComponent,
        CompteRenduSuiviDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDCompteRenduSuiviModule {}
