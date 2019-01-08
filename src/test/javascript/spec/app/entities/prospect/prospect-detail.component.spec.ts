/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { ProspectDetailComponent } from 'app/entities/prospect/prospect-detail.component';
import { Prospect } from 'app/shared/model/prospect.model';

describe('Component Tests', () => {
    describe('Prospect Management Detail Component', () => {
        let comp: ProspectDetailComponent;
        let fixture: ComponentFixture<ProspectDetailComponent>;
        const route = ({ data: of({ prospect: new Prospect('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [ProspectDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProspectDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProspectDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.prospect).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
