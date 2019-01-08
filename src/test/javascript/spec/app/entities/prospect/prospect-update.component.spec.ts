/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FireDTestModule } from '../../../test.module';
import { ProspectUpdateComponent } from 'app/entities/prospect/prospect-update.component';
import { ProspectService } from 'app/entities/prospect/prospect.service';
import { Prospect } from 'app/shared/model/prospect.model';

describe('Component Tests', () => {
    describe('Prospect Management Update Component', () => {
        let comp: ProspectUpdateComponent;
        let fixture: ComponentFixture<ProspectUpdateComponent>;
        let service: ProspectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [ProspectUpdateComponent]
            })
                .overrideTemplate(ProspectUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProspectUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProspectService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Prospect('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.prospect = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Prospect();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.prospect = entity;
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
