import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISuivi } from 'app/shared/model/suivi.model';

type EntityResponseType = HttpResponse<ISuivi>;
type EntityArrayResponseType = HttpResponse<ISuivi[]>;

@Injectable({ providedIn: 'root' })
export class SuiviService {
    public resourceUrl = SERVER_API_URL + 'api/suivis';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/suivis';

    constructor(protected http: HttpClient) {}

    create(suivi: ISuivi): Observable<EntityResponseType> {
        return this.http.post<ISuivi>(this.resourceUrl, suivi, { observe: 'response' });
    }

    update(suivi: ISuivi): Observable<EntityResponseType> {
        return this.http.put<ISuivi>(this.resourceUrl, suivi, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ISuivi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISuivi[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISuivi[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
