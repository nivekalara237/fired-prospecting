import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import { FireDAdminModule } from 'app/admin/admin.module';
import {
    SuiviComponent,
    SuiviDetailComponent,
    SuiviUpdateComponent,
    SuiviDeletePopupComponent,
    SuiviDeleteDialogComponent,
    suiviRoute,
    suiviPopupRoute
} from './';

const ENTITY_STATES = [...suiviRoute, ...suiviPopupRoute];

@NgModule({
    imports: [FireDSharedModule, FireDAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SuiviComponent, SuiviDetailComponent, SuiviUpdateComponent, SuiviDeleteDialogComponent, SuiviDeletePopupComponent],
    entryComponents: [SuiviComponent, SuiviUpdateComponent, SuiviDeleteDialogComponent, SuiviDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDSuiviModule {}
