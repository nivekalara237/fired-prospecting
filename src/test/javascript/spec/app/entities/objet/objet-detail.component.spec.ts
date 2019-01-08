/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { ObjetDetailComponent } from 'app/entities/objet/objet-detail.component';
import { Objet } from 'app/shared/model/objet.model';

describe('Component Tests', () => {
    describe('Objet Management Detail Component', () => {
        let comp: ObjetDetailComponent;
        let fixture: ComponentFixture<ObjetDetailComponent>;
        const route = ({ data: of({ objet: new Objet('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [ObjetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ObjetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObjetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.objet).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
