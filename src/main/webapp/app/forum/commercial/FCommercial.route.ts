import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { FCommercialComponent } from './FCommercial.component';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';
import { IMessage, Message } from '../../shared/model/message.model';
import { MessageFirestoreService } from '../../entities/message/message.firestore.service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/internal/operators';

@Injectable({ providedIn: 'root' })
export class MessageResolve implements Resolve<IMessage> {
    constructor(private service: MessageFirestoreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Message> {
        /*const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Message>) => response.ok),
                map((rapport: HttpResponse<Message>) => rapport.body)
            );
        }*/
        return of(new Message());
    }
}
export const routerFCommercial: Routes = [
    {
        path: 'forum-com',
        component: FCommercialComponent,
        resolve: {
            //pagingParams: JhiResolvePagingParams
            message: MessageResolve
        },

        data: {
            authorities: [],
            defaultSort: 'id,asc',
            pageTitle: 'fireDApp.forum.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
