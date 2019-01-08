/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { CopieUpdateComponent } from 'app/entities/copie/copie-update.component';
import { CopieService } from 'app/entities/copie/copie.service';
import { Copie } from 'app/shared/model/copie.model';

describe('Component Tests', () => {
    describe('Copie Management Update Component', () => {
        let comp: CopieUpdateComponent;
        let fixture: ComponentFixture<CopieUpdateComponent>;
        let service: CopieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [CopieUpdateComponent]
            })
                .overrideTemplate(CopieUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CopieUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CopieService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Copie('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.copie = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Copie();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.copie = entity;
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
