/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FireDTestModule } from '../../../test.module';
import { ObjetDeleteDialogComponent } from 'app/entities/objet/objet-delete-dialog.component';
import { ObjetService } from 'app/entities/objet/objet.service';

describe('Component Tests', () => {
    describe('Objet Management Delete Component', () => {
        let comp: ObjetDeleteDialogComponent;
        let fixture: ComponentFixture<ObjetDeleteDialogComponent>;
        let service: ObjetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [ObjetDeleteDialogComponent]
            })
                .overrideTemplate(ObjetDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObjetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObjetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
