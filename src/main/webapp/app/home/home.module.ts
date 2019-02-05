import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {
    NbSidebarModule,
    NbLayoutModule,
    NbSidebarService,
    NbButtonModule,
    NbCardModule,
    NbListModule,
    NbInputModule
} from '@nebular/theme';

import { FireDSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    imports: [
        FireDSharedModule,
        RouterModule.forChild([HOME_ROUTE]),
        NbLayoutModule,
        NbSidebarModule,
        NbButtonModule,
        NbCardModule,
        NbListModule,
        NbInputModule
    ],
    providers: [NbSidebarService], // we need this service for the sidebar
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDHomeModule {}
