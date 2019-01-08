import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Prospect } from 'app/shared/model/prospect.model';
import { ProspectService } from './prospect.service';
import { ProspectComponent } from './prospect.component';
import { ProspectDetailComponent } from './prospect-detail.component';
import { ProspectUpdateComponent } from './prospect-update.component';
import { ProspectDeletePopupComponent } from './prospect-delete-dialog.component';
import { IProspect } from 'app/shared/model/prospect.model';

@Injectable({ providedIn: 'root' })
export class ProspectResolve implements Resolve<IProspect> {
    constructor(private service: ProspectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Prospect> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Prospect>) => response.ok),
                map((prospect: HttpResponse<Prospect>) => prospect.body)
            );
        }
        return of(new Prospect());
    }
}

export const prospectRoute: Routes = [
    {
        path: 'prospect',
        component: ProspectComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'fireDApp.prospect.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prospect/:id/view',
        component: ProspectDetailComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.prospect.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prospect/new',
        component: ProspectUpdateComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.prospect.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prospect/:id/edit',
        component: ProspectUpdateComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.prospect.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prospectPopupRoute: Routes = [
    {
        path: 'prospect/:id/delete',
        component: ProspectDeletePopupComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.prospect.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
