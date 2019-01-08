import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FireDSharedModule } from 'app/shared';
import { FireDAdminModule } from 'app/admin/admin.module';
import {
    ChannelComponent,
    ChannelDetailComponent,
    ChannelUpdateComponent,
    ChannelDeletePopupComponent,
    ChannelDeleteDialogComponent,
    channelRoute,
    channelPopupRoute
} from './';

const ENTITY_STATES = [...channelRoute, ...channelPopupRoute];

@NgModule({
    imports: [FireDSharedModule, FireDAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChannelComponent,
        ChannelDetailComponent,
        ChannelUpdateComponent,
        ChannelDeleteDialogComponent,
        ChannelDeletePopupComponent
    ],
    entryComponents: [ChannelComponent, ChannelUpdateComponent, ChannelDeleteDialogComponent, ChannelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDChannelModule {}
