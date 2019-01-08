import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';
import { CompteRenduSuiviService } from './compte-rendu-suivi.service';
import { CompteRenduSuiviComponent } from './compte-rendu-suivi.component';
import { CompteRenduSuiviDetailComponent } from './compte-rendu-suivi-detail.component';
import { CompteRenduSuiviUpdateComponent } from './compte-rendu-suivi-update.component';
import { CompteRenduSuiviDeletePopupComponent } from './compte-rendu-suivi-delete-dialog.component';
import { ICompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';

@Injectable({ providedIn: 'root' })
export class CompteRenduSuiviResolve implements Resolve<ICompteRenduSuivi> {
    constructor(private service: CompteRenduSuiviService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CompteRenduSuivi> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CompteRenduSuivi>) => response.ok),
                map((compteRenduSuivi: HttpResponse<CompteRenduSuivi>) => compteRenduSuivi.body)
            );
        }
        return of(new CompteRenduSuivi());
    }
}

export const compteRenduSuiviRoute: Routes = [
    {
        path: 'compte-rendu-suivi',
        component: CompteRenduSuiviComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.compteRenduSuivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compte-rendu-suivi/:id/view',
        component: CompteRenduSuiviDetailComponent,
        resolve: {
            compteRenduSuivi: CompteRenduSuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.compteRenduSuivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compte-rendu-suivi/new',
        component: CompteRenduSuiviUpdateComponent,
        resolve: {
            compteRenduSuivi: CompteRenduSuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.compteRenduSuivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compte-rendu-suivi/:id/edit',
        component: CompteRenduSuiviUpdateComponent,
        resolve: {
            compteRenduSuivi: CompteRenduSuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.compteRenduSuivi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compteRenduSuiviPopupRoute: Routes = [
    {
        path: 'compte-rendu-suivi/:id/delete',
        component: CompteRenduSuiviDeletePopupComponent,
        resolve: {
            compteRenduSuivi: CompteRenduSuiviResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.compteRenduSuivi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
