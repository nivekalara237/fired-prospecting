import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProspect } from 'app/shared/model/prospect.model';

@Component({
    selector: 'jhi-prospect-detail',
    templateUrl: './prospect-detail.component.html'
})
export class ProspectDetailComponent implements OnInit {
    prospect: IProspect;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ prospect }) => {
            this.prospect = prospect;
        });
    }

    previousState() {
        window.history.back();
    }
}
