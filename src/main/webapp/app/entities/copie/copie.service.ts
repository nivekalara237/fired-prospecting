import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICopie } from 'app/shared/model/copie.model';

type EntityResponseType = HttpResponse<ICopie>;
type EntityArrayResponseType = HttpResponse<ICopie[]>;

@Injectable({ providedIn: 'root' })
export class CopieService {
    public resourceUrl = SERVER_API_URL + 'api/copies';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/copies';

    constructor(protected http: HttpClient) {}

    create(copie: ICopie): Observable<EntityResponseType> {
        return this.http.post<ICopie>(this.resourceUrl, copie, { observe: 'response' });
    }

    update(copie: ICopie): Observable<EntityResponseType> {
        return this.http.put<ICopie>(this.resourceUrl, copie, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ICopie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICopie[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICopie[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
