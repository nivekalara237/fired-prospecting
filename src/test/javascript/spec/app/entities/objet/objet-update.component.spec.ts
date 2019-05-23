/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { ObjetUpdateComponent } from 'app/entities/objet/objet-update.component';
import { ObjetService } from 'app/entities/objet/objet.service';
import { Objet } from 'app/shared/model/objet.model';

describe('Component Tests', () => {
    describe('PieceJointe Management Update Component', () => {
        let comp: ObjetUpdateComponent;
        let fixture: ComponentFixture<ObjetUpdateComponent>;
        let service: ObjetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [ObjetUpdateComponent]
            })
                .overrideTemplate(ObjetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ObjetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObjetService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Objet('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.objet = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Objet();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.objet = entity;
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
