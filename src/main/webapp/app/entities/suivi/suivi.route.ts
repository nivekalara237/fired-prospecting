import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Suivi } from 'app/shared/model/suivi.model';
import { SuiviService } from './suivi.service';
import { SuiviComponent } from './suivi.component';
import { SuiviDetailComponent } from './suivi-detail.component';
import { SuiviUpdateComponent } from './suivi-update.component';
import { SuiviDeletePopupComponent } from './suivi-delete-dialog.component';
import { ISuivi } from 'app/shared/model/suivi.model';

@Injectable({ providedIn: 'root' })
export class SuiviResolve implements Resolve<ISuivi> {
    constructor(private service: SuiviService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Suivi> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Suivi>) => response.ok),
                map((suivi: HttpResponse<Suivi>) => suivi.body)
            );
        }
        return of(new Suivi());
    }
}

export const suiviRoute: Routes = [
    {
        path: 'suivi',
        component: SuiviComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'fireDApp.suivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'suivi/:id/view',
        component: SuiviDetailComponent,
        resolve: {
            suivi: SuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.suivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'suivi/new',
        component: SuiviUpdateComponent,
        resolve: {
            suivi: SuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.suivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'suivi/:id/edit',
        component: SuiviUpdateComponent,
        resolve: {
            suivi: SuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.suivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const suiviPopupRoute: Routes = [
    {
        path: 'suivi/:id/delete',
        component: SuiviDeletePopupComponent,
        resolve: {
            suivi: SuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.suivi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
