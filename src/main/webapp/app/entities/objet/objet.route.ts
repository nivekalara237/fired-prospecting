import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Objet } from 'app/shared/model/objet.model';
import { ObjetService } from './objet.service';
import { ObjetComponent } from './objet.component';
import { ObjetDetailComponent } from './objet-detail.component';
import { ObjetUpdateComponent } from './objet-update.component';
import { ObjetDeletePopupComponent } from './objet-delete-dialog.component';
import { IObjet } from 'app/shared/model/objet.model';

@Injectable({ providedIn: 'root' })
export class ObjetResolve implements Resolve<IObjet> {
    constructor(private service: ObjetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Objet> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Objet>) => response.ok),
                map((objet: HttpResponse<Objet>) => objet.body)
            );
        }
        return of(new Objet());
    }
}

export const objetRoute: Routes = [
    {
        path: 'objet',
        component: ObjetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.objet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'objet/:id/view',
        component: ObjetDetailComponent,
        resolve: {
            objet: ObjetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.objet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'objet/new',
        component: ObjetUpdateComponent,
        resolve: {
            objet: ObjetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.objet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'objet/:id/edit',
        component: ObjetUpdateComponent,
        resolve: {
            objet: ObjetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.objet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const objetPopupRoute: Routes = [
    {
        path: 'objet/:id/delete',
        component: ObjetDeletePopupComponent,
        resolve: {
            objet: ObjetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.objet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
