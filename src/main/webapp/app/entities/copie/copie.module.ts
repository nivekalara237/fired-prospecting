import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import {
    CopieComponent,
    CopieDetailComponent,
    CopieUpdateComponent,
    CopieDeletePopupComponent,
    CopieDeleteDialogComponent,
    copieRoute,
    copiePopupRoute
} from './';

const ENTITY_STATES = [...copieRoute, ...copiePopupRoute];

@NgModule({
    imports: [FireDSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CopieComponent, CopieDetailComponent, CopieUpdateComponent, CopieDeleteDialogComponent, CopieDeletePopupComponent],
    entryComponents: [CopieComponent, CopieUpdateComponent, CopieDeleteDialogComponent, CopieDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDCopieModule {}
