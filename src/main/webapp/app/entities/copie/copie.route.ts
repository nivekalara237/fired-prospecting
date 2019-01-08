import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Copie } from 'app/shared/model/copie.model';
import { CopieService } from './copie.service';
import { CopieComponent } from './copie.component';
import { CopieDetailComponent } from './copie-detail.component';
import { CopieUpdateComponent } from './copie-update.component';
import { CopieDeletePopupComponent } from './copie-delete-dialog.component';
import { ICopie } from 'app/shared/model/copie.model';

@Injectable({ providedIn: 'root' })
export class CopieResolve implements Resolve<ICopie> {
    constructor(private service: CopieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Copie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Copie>) => response.ok),
                map((copie: HttpResponse<Copie>) => copie.body)
            );
        }
        return of(new Copie());
    }
}

export const copieRoute: Routes = [
    {
        path: 'copie',
        component: CopieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.copie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'copie/:id/view',
        component: CopieDetailComponent,
        resolve: {
            copie: CopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.copie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'copie/new',
        component: CopieUpdateComponent,
        resolve: {
            copie: CopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.copie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'copie/:id/edit',
        component: CopieUpdateComponent,
        resolve: {
            copie: CopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.copie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const copiePopupRoute: Routes = [
    {
        path: 'copie/:id/delete',
        component: CopieDeletePopupComponent,
        resolve: {
            copie: CopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fireDApp.copie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
