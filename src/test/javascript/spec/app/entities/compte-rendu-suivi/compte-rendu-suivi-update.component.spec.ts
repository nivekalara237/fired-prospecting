/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { CompteRenduSuiviUpdateComponent } from 'app/entities/compte-rendu-suivi/compte-rendu-suivi-update.component';
import { CompteRenduSuiviService } from 'app/entities/compte-rendu-suivi/compte-rendu-suivi.service';
import { CompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';

describe('Component Tests', () => {
    describe('CompteRenduSuivi Management Update Component', () => {
        let comp: CompteRenduSuiviUpdateComponent;
        let fixture: ComponentFixture<CompteRenduSuiviUpdateComponent>;
        let service: CompteRenduSuiviService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [CompteRenduSuiviUpdateComponent]
            })
                .overrideTemplate(CompteRenduSuiviUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompteRenduSuiviUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteRenduSuiviService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CompteRenduSuivi('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.compteRenduSuivi = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CompteRenduSuivi();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.compteRenduSuivi = entity;
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
