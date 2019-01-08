import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEntreprise } from 'app/shared/model/entreprise.model';

type EntityResponseType = HttpResponse<IEntreprise>;
type EntityArrayResponseType = HttpResponse<IEntreprise[]>;

@Injectable({ providedIn: 'root' })
export class EntrepriseService {
    public resourceUrl = SERVER_API_URL + 'api/entreprises';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/entreprises';

    constructor(protected http: HttpClient) {}

    create(entreprise: IEntreprise): Observable<EntityResponseType> {
        return this.http.post<IEntreprise>(this.resourceUrl, entreprise, { observe: 'response' });
    }

    update(entreprise: IEntreprise): Observable<EntityResponseType> {
        return this.http.put<IEntreprise>(this.resourceUrl, entreprise, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IEntreprise>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEntreprise[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEntreprise[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
