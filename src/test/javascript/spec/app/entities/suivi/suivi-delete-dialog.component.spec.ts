/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FireDTestModule } from '../../../test.module';
import { SuiviDeleteDialogComponent } from 'app/entities/suivi/suivi-delete-dialog.component';
import { SuiviService } from 'app/entities/suivi/suivi.service';

describe('Component Tests', () => {
    describe('Suivi Management Delete Component', () => {
        let comp: SuiviDeleteDialogComponent;
        let fixture: ComponentFixture<SuiviDeleteDialogComponent>;
        let service: SuiviService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [SuiviDeleteDialogComponent]
            })
                .overrideTemplate(SuiviDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SuiviDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SuiviService);
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
