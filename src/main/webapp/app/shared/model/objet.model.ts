export interface IObjet {
    id?: string;
    nom?: string;
    lien?: string;
    encode?: string;
    rapportId?: string;
}

export class Objet implements IObjet {
    constructor(public id?: string, public nom?: string, public lien?: string, public encode?: string, public rapportId?: string) {}
}
