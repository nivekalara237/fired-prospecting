/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FireDTestModule } from '../../../test.module';
import { RapportDeleteDialogComponent } from 'app/entities/rapport/rapport-delete-dialog.component';
import { RapportService } from 'app/entities/rapport/rapport.service';

describe('Component Tests', () => {
    describe('Rapport Management Delete Component', () => {
        let comp: RapportDeleteDialogComponent;
        let fixture: ComponentFixture<RapportDeleteDialogComponent>;
        let service: RapportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [RapportDeleteDialogComponent]
            })
                .overrideTemplate(RapportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RapportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RapportService);
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
