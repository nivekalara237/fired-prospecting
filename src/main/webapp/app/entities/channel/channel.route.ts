import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Channel } from 'app/shared/model/channel.model';
import { ChannelService } from './channel.service';
import { ChannelComponent } from './channel.component';
import { ChannelDetailComponent } from './channel-detail.component';
import { ChannelUpdateComponent } from './channel-update.component';
import { ChannelDeletePopupComponent } from './channel-delete-dialog.component';
import { IChannel } from 'app/shared/model/channel.model';

@Injectable({ providedIn: 'root' })
export class ChannelResolve implements Resolve<IChannel> {
    constructor(private service: ChannelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Channel> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Channel>) => response.ok),
                map((channel: HttpResponse<Channel>) => channel.body)
            );
        }
        return of(new Channel());
    }
}

export const channelRoute: Routes = [
    {
        path: 'channel',
        component: ChannelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.channel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'channel/:id/view',
        component: ChannelDetailComponent,
        resolve: {
            channel: ChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.channel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'channel/new',
        component: ChannelUpdateComponent,
        resolve: {
            channel: ChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.channel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'channel/:id/edit',
        component: ChannelUpdateComponent,
        resolve: {
            channel: ChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.channel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const channelPopupRoute: Routes = [
    {
        path: 'channel/:id/delete',
        component: ChannelDeletePopupComponent,
        resolve: {
            channel: ChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.channel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
