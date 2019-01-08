/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { RapportUpdateComponent } from 'app/entities/rapport/rapport-update.component';
import { RapportService } from 'app/entities/rapport/rapport.service';
import { Rapport } from 'app/shared/model/rapport.model';

describe('Component Tests', () => {
    describe('Rapport Management Update Component', () => {
        let comp: RapportUpdateComponent;
        let fixture: ComponentFixture<RapportUpdateComponent>;
        let service: RapportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [RapportUpdateComponent]
            })
                .overrideTemplate(RapportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RapportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RapportService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Rapport('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rapport = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Rapport();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rapport = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
