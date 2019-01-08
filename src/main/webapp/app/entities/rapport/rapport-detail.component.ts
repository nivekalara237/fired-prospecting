import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRapport } from 'app/shared/model/rapport.model';

@Component({
    selector: 'jhi-rapport-detail',
    templateUrl: './rapport-detail.component.html'
})
export class RapportDetailComponent implements OnInit {
    rapport: IRapport;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rapport }) => {
            this.rapport = rapport;
        });
    }

    previousState() {
        window.history.back();
    }
}
