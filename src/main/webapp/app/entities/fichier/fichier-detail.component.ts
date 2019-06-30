import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFichier } from 'app/shared/model/fichier.model';

@Component({
    selector: 'jhi-fichier-detail',
    templateUrl: './fichier-detail.component.html'
})
export class FichierDetailComponent implements OnInit {
    fichier: IFichier;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fichier }) => {
            this.fichier = fichier;
        });
    }

    previousState() {
        window.history.back();
    }
}
