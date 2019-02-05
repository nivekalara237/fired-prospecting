export interface ICopie {
    id?: string;
    email?: string;
    createdAt?: string;
    rapportId?: string;
}

export class Copie implements ICopie {
    constructor(public id?: string, public email?: string, public createdAt?: string, public rapportId?: string) {}
}
