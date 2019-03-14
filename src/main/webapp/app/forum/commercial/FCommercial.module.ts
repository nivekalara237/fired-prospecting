import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import { FireDAdminModule } from 'app/admin/admin.module';
import { routerFCommercial } from './FCommercial.route';
import { FCommercialComponent } from './FCommercial.component';

const ENTITY_STATES = [...routerFCommercial];

@NgModule({
    imports: [FireDSharedModule, FireDAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FCommercialComponent],
    entryComponents: [FCommercialComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FCommercialModule {}
