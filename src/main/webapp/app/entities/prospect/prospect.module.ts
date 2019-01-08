import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import { FireDAdminModule } from 'app/admin/admin.module';
import {
    ProspectComponent,
    ProspectDetailComponent,
    ProspectUpdateComponent,
    ProspectDeletePopupComponent,
    ProspectDeleteDialogComponent,
    prospectRoute,
    prospectPopupRoute
} from './';

const ENTITY_STATES = [...prospectRoute, ...prospectPopupRoute];

@NgModule({
    imports: [FireDSharedModule, FireDAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProspectComponent,
        ProspectDetailComponent,
        ProspectUpdateComponent,
        ProspectDeleteDialogComponent,
        ProspectDeletePopupComponent
    ],
    entryComponents: [ProspectComponent, ProspectUpdateComponent, ProspectDeleteDialogComponent, ProspectDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDProspectModule {}
