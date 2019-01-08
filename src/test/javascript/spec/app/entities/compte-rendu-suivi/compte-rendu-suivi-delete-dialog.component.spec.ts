/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FireDTestModule } from '../../../test.module';
import { CompteRenduSuiviDeleteDialogComponent } from 'app/entities/compte-rendu-suivi/compte-rendu-suivi-delete-dialog.component';
import { CompteRenduSuiviService } from 'app/entities/compte-rendu-suivi/compte-rendu-suivi.service';

describe('Component Tests', () => {
    describe('CompteRenduSuivi Management Delete Component', () => {
        let comp: CompteRenduSuiviDeleteDialogComponent;
        let fixture: ComponentFixture<CompteRenduSuiviDeleteDialogComponent>;
        let service: CompteRenduSuiviService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [CompteRenduSuiviDeleteDialogComponent]
            })
                .overrideTemplate(CompteRenduSuiviDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompteRenduSuiviDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteRenduSuiviService);
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
