import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntreprise } from 'app/shared/model/entreprise.model';

@Component({
    selector: 'jhi-entreprise-detail',
    templateUrl: './entreprise-detail.component.html'
})
export class EntrepriseDetailComponent implements OnInit {
    entreprise: IEntreprise;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entreprise }) => {
            this.entreprise = entreprise;
        });
    }

    previousState() {
        window.history.back();
    }
}
