/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FireDTestModule } from '../../../test.module';
import { EntrepriseDeleteDialogComponent } from 'app/entities/entreprise/entreprise-delete-dialog.component';
import { EntrepriseService } from 'app/entities/entreprise/entreprise.service';

describe('Component Tests', () => {
    describe('Entreprise Management Delete Component', () => {
        let comp: EntrepriseDeleteDialogComponent;
        let fixture: ComponentFixture<EntrepriseDeleteDialogComponent>;
        let service: EntrepriseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FireDTestModule],
                declarations: [EntrepriseDeleteDialogComponent]
            })
                .overrideTemplate(EntrepriseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EntrepriseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntrepriseService);
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
