import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import { FireDAdminModule } from 'app/admin/admin.module';
import {
    EntrepriseComponent,
    EntrepriseDetailComponent,
    EntrepriseUpdateComponent,
    EntrepriseDeletePopupComponent,
    EntrepriseDeleteDialogComponent,
    entrepriseRoute,
    entreprisePopupRoute
} from './';

const ENTITY_STATES = [...entrepriseRoute, ...entreprisePopupRoute];

@NgModule({
    imports: [FireDSharedModule, FireDAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EntrepriseComponent,
        EntrepriseDetailComponent,
        EntrepriseUpdateComponent,
        EntrepriseDeleteDialogComponent,
        EntrepriseDeletePopupComponent
    ],
    entryComponents: [EntrepriseComponent, EntrepriseUpdateComponent, EntrepriseDeleteDialogComponent, EntrepriseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDEntrepriseModule {}
