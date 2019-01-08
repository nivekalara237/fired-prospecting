import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';

@Component({
    selector: 'jhi-compte-rendu-suivi-detail',
    templateUrl: './compte-rendu-suivi-detail.component.html'
})
export class CompteRenduSuiviDetailComponent implements OnInit {
    compteRenduSuivi: ICompteRenduSuivi;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compteRenduSuivi }) => {
            this.compteRenduSuivi = compteRenduSuivi;
        });
    }

    previousState() {
        window.history.back();
    }
}
