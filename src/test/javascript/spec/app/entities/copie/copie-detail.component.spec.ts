/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { CopieDetailComponent } from 'app/entities/copie/copie-detail.component';
import { Copie } from 'app/shared/model/copie.model';

describe('Component Tests', () => {
    describe('Copie Management Detail Component', () => {
        let comp: CopieDetailComponent;
        let fixture: ComponentFixture<CopieDetailComponent>;
        const route = ({ data: of({ copie: new Copie('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [CopieDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CopieDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CopieDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.copie).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
