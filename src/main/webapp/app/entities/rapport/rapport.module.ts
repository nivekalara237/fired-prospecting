import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FileUploadModule } from 'ng2-file-upload';

import { FireDSharedModule } from 'app/shared';
import { FireDAdminModule } from 'app/admin/admin.module';
import {
    RapportComponent,
    RapportDetailComponent,
    RapportUpdateComponent,
    RapportDeletePopupComponent,
    RapportDeleteDialogComponent,
    rapportRoute,
    rapportPopupRoute
} from './';

const ENTITY_STATES = [...rapportRoute, ...rapportPopupRoute];

@NgModule({
    imports: [FireDSharedModule, FileUploadModule, FireDAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RapportComponent,
        RapportDetailComponent,
        RapportUpdateComponent,
        RapportDeleteDialogComponent,
        RapportDeletePopupComponent
    ],
    entryComponents: [RapportComponent, RapportUpdateComponent, RapportDeleteDialogComponent, RapportDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDRapportModule {}
