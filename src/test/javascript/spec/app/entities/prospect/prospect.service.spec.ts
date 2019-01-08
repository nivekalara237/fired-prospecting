/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ProspectService } from 'app/entities/prospect/prospect.service';
import { IProspect, Prospect } from 'app/shared/model/prospect.model';

describe('Service Tests', () => {
    describe('Prospect Service', () => {
        let injector: TestBed;
        let service: ProspectService;
        let httpMock: HttpTestingController;
        let elemDefault: IProspect;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProspectService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Prospect(
                'ID',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
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

            it('should create a Prospect', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Prospect(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Prospect', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        email: 'BBBBBB',
                        telephone: 'BBBBBB',
                        type: 1,
                        dateRdv: 'BBBBBB',
                        compteRendu: 'BBBBBB',
                        localisation: 'BBBBBB',
                        position: 'BBBBBB',
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

            it('should return a list of Prospect', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        email: 'BBBBBB',
                        telephone: 'BBBBBB',
                        type: 1,
                        dateRdv: 'BBBBBB',
                        compteRendu: 'BBBBBB',
                        localisation: 'BBBBBB',
                        position: 'BBBBBB',
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

            it('should delete a Prospect', async () => {
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
