import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Entreprise } from 'app/shared/model/entreprise.model';
import { EntrepriseService } from './entreprise.service';
import { EntrepriseComponent } from './entreprise.component';
import { EntrepriseDetailComponent } from './entreprise-detail.component';
import { EntrepriseUpdateComponent } from './entreprise-update.component';
import { EntrepriseDeletePopupComponent } from './entreprise-delete-dialog.component';
import { IEntreprise } from 'app/shared/model/entreprise.model';

@Injectable({ providedIn: 'root' })
export class EntrepriseResolve implements Resolve<IEntreprise> {
    constructor(private service: EntrepriseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Entreprise> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Entreprise>) => response.ok),
                map((entreprise: HttpResponse<Entreprise>) => entreprise.body)
            );
        }
        return of(new Entreprise());
    }
}

export const entrepriseRoute: Routes = [
    {
        path: 'entreprise',
        component: EntrepriseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entreprise/:id/view',
        component: EntrepriseDetailComponent,
        resolve: {
            entreprise: EntrepriseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entreprise/new',
        component: EntrepriseUpdateComponent,
        resolve: {
            entreprise: EntrepriseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entreprise/:id/edit',
        component: EntrepriseUpdateComponent,
        resolve: {
            entreprise: EntrepriseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entreprisePopupRoute: Routes = [
    {
        path: 'entreprise/:id/delete',
        component: EntrepriseDeletePopupComponent,
        resolve: {
            entreprise: EntrepriseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
