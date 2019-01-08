/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { SuiviUpdateComponent } from 'app/entities/suivi/suivi-update.component';
import { SuiviService } from 'app/entities/suivi/suivi.service';
import { Suivi } from 'app/shared/model/suivi.model';

describe('Component Tests', () => {
    describe('Suivi Management Update Component', () => {
        let comp: SuiviUpdateComponent;
        let fixture: ComponentFixture<SuiviUpdateComponent>;
        let service: SuiviService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [SuiviUpdateComponent]
            })
                .overrideTemplate(SuiviUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SuiviUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SuiviService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Suivi('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.suivi = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Suivi();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.suivi = entity;
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
