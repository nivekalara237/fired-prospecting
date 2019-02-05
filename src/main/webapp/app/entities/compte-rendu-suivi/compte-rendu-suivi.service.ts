import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompteRenduSuivi } from 'app/shared/model/compte-rendu-suivi.model';

type EntityResponseType = HttpResponse<ICompteRenduSuivi>;
type EntityArrayResponseType = HttpResponse<ICompteRenduSuivi[]>;

@Injectable({ providedIn: 'root' })
export class CompteRenduSuiviService {
    public resourceUrl = SERVER_API_URL + 'api/compte-rendu-suivis';
    public resourceUrlProspect = SERVER_API_URL + 'api/prospects';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/compte-rendu-suivis';

    constructor(protected http: HttpClient) {}

    create(compteRenduSuivi: ICompteRenduSuivi): Observable<EntityResponseType> {
        return this.http.post<ICompteRenduSuivi>(this.resourceUrl, compteRenduSuivi, { observe: 'response' });
    }
    createInProspectPage(compteRenduSuivi: ICompteRenduSuivi): Observable<EntityResponseType> {
        return this.http.post<ICompteRenduSuivi>(this.resourceUrl, compteRenduSuivi, { observe: 'response' });
    }

    update(compteRenduSuivi: ICompteRenduSuivi): Observable<EntityResponseType> {
        return this.http.put<ICompteRenduSuivi>(this.resourceUrl, compteRenduSuivi, { observe: 'response' });
    }

    find(prospect: string): Observable<EntityResponseType> {
        return this.http.get<ICompteRenduSuivi>(`${this.resourceUrl}/${prospect}`, { observe: 'response' });
    }

    findByProspect(id: string): Observable<EntityArrayResponseType> {
        // const options = createRequestOption(id);
        return this.http.get<ICompteRenduSuivi[]>(`${this.resourceUrl}/by-prospect/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICompteRenduSuivi[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICompteRenduSuivi[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
