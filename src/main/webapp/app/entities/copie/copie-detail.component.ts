import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICopie } from 'app/shared/model/copie.model';

@Component({
    selector: 'jhi-copie-detail',
    templateUrl: './copie-detail.component.html'
})
export class CopieDetailComponent implements OnInit {
    copie: ICopie;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ copie }) => {
            this.copie = copie;
        });
    }

    previousState() {
        window.history.back();
    }
}
