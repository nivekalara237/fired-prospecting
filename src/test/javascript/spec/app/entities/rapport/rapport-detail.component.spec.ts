/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { RapportDetailComponent } from 'app/entities/rapport/rapport-detail.component';
import { Rapport } from 'app/shared/model/rapport.model';

describe('Component Tests', () => {
    describe('Rapport Management Detail Component', () => {
        let comp: RapportDetailComponent;
        let fixture: ComponentFixture<RapportDetailComponent>;
        const route = ({ data: of({ rapport: new Rapport('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [RapportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RapportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RapportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rapport).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
