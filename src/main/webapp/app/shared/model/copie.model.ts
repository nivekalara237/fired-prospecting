import { IRapport } from 'app/shared/model//rapport.model';

export interface ICopie {
    id?: string;
    email?: string;
    createdAt?: string;
    rapport?: IRapport;
}

export class Copie implements ICopie {
    constructor(public id?: string, public email?: string, public createdAt?: string, public rapport?: IRapport) {}
}
