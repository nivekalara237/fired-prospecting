import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IFichier } from 'app/shared/model/fichier.model';
import { FichierService } from './fichier.service';

@Component({
    selector: 'jhi-fichier-update',
    templateUrl: './fichier-update.component.html'
})
export class FichierUpdateComponent implements OnInit {
    fichier: IFichier;
    isSaving: boolean;

    constructor(protected fichierService: FichierService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fichier }) => {
            this.fichier = fichier;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fichier.id !== undefined) {
            this.subscribeToSaveResponse(this.fichierService.update(this.fichier));
        } else {
            this.subscribeToSaveResponse(this.fichierService.create(this.fichier));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichier>>) {
        result.subscribe((res: HttpResponse<IFichier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
