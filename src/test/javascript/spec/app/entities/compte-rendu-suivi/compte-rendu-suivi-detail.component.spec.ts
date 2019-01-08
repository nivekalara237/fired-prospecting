/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { CompteRenduSuiviDetailComponent } from 'app/entities/compte-rendu-suivi/compte-rendu-suivi-detail.component';
import { CompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';

describe('Component Tests', () => {
    describe('CompteRenduSuivi Management Detail Component', () => {
        let comp: CompteRenduSuiviDetailComponent;
        let fixture: ComponentFixture<CompteRenduSuiviDetailComponent>;
        const route = ({ data: of({ compteRenduSuivi: new CompteRenduSuivi('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [CompteRenduSuiviDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompteRenduSuiviDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompteRenduSuiviDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.compteRenduSuivi).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
