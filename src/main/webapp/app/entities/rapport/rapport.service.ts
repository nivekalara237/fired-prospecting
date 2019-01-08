import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRapport } from 'app/shared/model/rapport.model';

type EntityResponseType = HttpResponse<IRapport>;
type EntityArrayResponseType = HttpResponse<IRapport[]>;

@Injectable({ providedIn: 'root' })
export class RapportService {
    public resourceUrl = SERVER_API_URL + 'api/rapports';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/rapports';

    constructor(protected http: HttpClient) {}

    create(rapport: IRapport): Observable<EntityResponseType> {
        return this.http.post<IRapport>(this.resourceUrl, rapport, { observe: 'response' });
    }

    update(rapport: IRapport): Observable<EntityResponseType> {
        return this.http.put<IRapport>(this.resourceUrl, rapport, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IRapport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRapport[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRapport[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
