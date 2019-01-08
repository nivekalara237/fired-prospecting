import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import {
    ObjetComponent,
    ObjetDetailComponent,
    ObjetUpdateComponent,
    ObjetDeletePopupComponent,
    ObjetDeleteDialogComponent,
    objetRoute,
    objetPopupRoute
} from './';

const ENTITY_STATES = [...objetRoute, ...objetPopupRoute];

@NgModule({
    imports: [FireDSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ObjetComponent, ObjetDetailComponent, ObjetUpdateComponent, ObjetDeleteDialogComponent, ObjetDeletePopupComponent],
    entryComponents: [ObjetComponent, ObjetUpdateComponent, ObjetDeleteDialogComponent, ObjetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDObjetModule {}
