import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Rapport } from 'app/shared/model/rapport.model';
import { RapportService } from './rapport.service';
import { RapportComponent } from './rapport.component';
import { RapportDetailComponent } from './rapport-detail.component';
import { RapportUpdateComponent } from './rapport-update.component';
import { RapportDeletePopupComponent } from './rapport-delete-dialog.component';
import { IRapport } from 'app/shared/model/rapport.model';

@Injectable({ providedIn: 'root' })
export class RapportResolve implements Resolve<IRapport> {
    constructor(private service: RapportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Rapport> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Rapport>) => response.ok),
                map((rapport: HttpResponse<Rapport>) => rapport.body)
            );
        }
        return of(new Rapport());
    }
}

export const rapportRoute: Routes = [
    {
        path: 'rapport',
        component: RapportComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'fireDApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rapport/:id/view',
        component: RapportDetailComponent,
        resolve: {
            rapport: RapportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rapport/new',
        component: RapportUpdateComponent,
        resolve: {
            rapport: RapportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rapport/:id/edit',
        component: RapportUpdateComponent,
        resolve: {
            rapport: RapportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rapportPopupRoute: Routes = [
    {
        path: 'rapport/:id/delete',
        component: RapportDeletePopupComponent,
        resolve: {
            rapport: RapportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
