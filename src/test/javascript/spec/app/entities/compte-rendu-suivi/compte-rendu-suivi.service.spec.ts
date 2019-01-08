/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CompteRenduSuiviService } from 'app/entities/compte-rendu-suivi/compte-rendu-suivi.service';
import { ICompteRenduSuivi, CompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';

describe('Service Tests', () => {
    describe('CompteRenduSuivi Service', () => {
        let injector: TestBed;
        let service: CompteRenduSuiviService;
        let httpMock: HttpTestingController;
        let elemDefault: ICompteRenduSuivi;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CompteRenduSuiviService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new CompteRenduSuivi('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find('123')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CompteRenduSuivi', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new CompteRenduSuivi(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CompteRenduSuivi', async () => {
                const returnedFromService = Object.assign(
                    {
                        contenu: 'BBBBBB',
                        dateProchaineRdv: 'BBBBBB',
                        createdAt: 'BBBBBB',
                        updatedAt: 'BBBBBB',
                        deletedAt: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CompteRenduSuivi', async () => {
                const returnedFromService = Object.assign(
                    {
                        contenu: 'BBBBBB',
                        dateProchaineRdv: 'BBBBBB',
                        createdAt: 'BBBBBB',
                        updatedAt: 'BBBBBB',
                        deletedAt: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a CompteRenduSuivi', async () => {
                const rxPromise = service.delete('123').subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
