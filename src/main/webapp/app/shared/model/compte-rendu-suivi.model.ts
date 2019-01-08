import { ISuivi } from 'app/shared/model//suivi.model';

export interface ICompteRenduSuivi {
    id?: string;
    contenu?: string;
    dateProchaineRdv?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    suivi?: ISuivi;
}

export class CompteRenduSuivi implements ICompteRenduSuivi {
    constructor(
        public id?: string,
        public contenu?: string,
        public dateProchaineRdv?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public suivi?: ISuivi
    ) {}
}
