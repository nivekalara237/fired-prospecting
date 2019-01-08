import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuivi } from 'app/shared/model/suivi.model';

@Component({
    selector: 'jhi-suivi-detail',
    templateUrl: './suivi-detail.component.html'
})
export class SuiviDetailComponent implements OnInit {
    suivi: ISuivi;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ suivi }) => {
            this.suivi = suivi;
        });
    }

    previousState() {
        window.history.back();
    }
}
