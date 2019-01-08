import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMessage } from 'app/shared/model/message.model';

type EntityResponseType = HttpResponse<IMessage>;
type EntityArrayResponseType = HttpResponse<IMessage[]>;

@Injectable({ providedIn: 'root' })
export class MessageService {
    public resourceUrl = SERVER_API_URL + 'api/messages';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/messages';

    constructor(protected http: HttpClient) {}

    create(message: IMessage): Observable<EntityResponseType> {
        return this.http.post<IMessage>(this.resourceUrl, message, { observe: 'response' });
    }

    update(message: IMessage): Observable<EntityResponseType> {
        return this.http.put<IMessage>(this.resourceUrl, message, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMessage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMessage[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
