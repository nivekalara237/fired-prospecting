import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IObjet } from 'app/shared/model/objet.model';

type EntityResponseType = HttpResponse<IObjet>;
type EntityArrayResponseType = HttpResponse<IObjet[]>;

@Injectable({ providedIn: 'root' })
export class ObjetService {
    public resourceUrl = SERVER_API_URL + 'api/objets';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/objets';

    constructor(protected http: HttpClient) {}

    create(objet: IObjet): Observable<EntityResponseType> {
        return this.http.post<IObjet>(this.resourceUrl, objet, { observe: 'response' });
    }

    update(objet: IObjet): Observable<EntityResponseType> {
        return this.http.put<IObjet>(this.resourceUrl, objet, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IObjet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IObjet[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IObjet[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
