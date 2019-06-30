import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFichier } from 'app/shared/model/fichier.model';

type EntityResponseType = HttpResponse<IFichier>;
type EntityArrayResponseType = HttpResponse<IFichier[]>;

@Injectable({ providedIn: 'root' })
export class FichierService {
    public resourceUrl = SERVER_API_URL + 'api/fichiers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/fichiers';

    constructor(protected http: HttpClient) {}

    create(fichier: IFichier): Observable<EntityResponseType> {
        return this.http.post<IFichier>(this.resourceUrl, fichier, { observe: 'response' });
    }

    update(fichier: IFichier): Observable<EntityResponseType> {
        return this.http.put<IFichier>(this.resourceUrl, fichier, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IFichier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFichier[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFichier[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
