/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { SuiviDetailComponent } from 'app/entities/suivi/suivi-detail.component';
import { Suivi } from 'app/shared/model/suivi.model';

describe('Component Tests', () => {
    describe('Suivi Management Detail Component', () => {
        let comp: SuiviDetailComponent;
        let fixture: ComponentFixture<SuiviDetailComponent>;
        const route = ({ data: of({ suivi: new Suivi('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [SuiviDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SuiviDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SuiviDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.suivi).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
