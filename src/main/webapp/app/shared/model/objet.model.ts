import { IRapport } from 'app/shared/model//rapport.model';

export interface IObjet {
    id?: string;
    nom?: string;
    lien?: string;
    encode?: string;
    rapport?: IRapport;
}

export class Objet implements IObjet {
    constructor(public id?: string, public nom?: string, public lien?: string, public encode?: string, public rapport?: IRapport) {}
}
