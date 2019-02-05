export interface ICompteRenduSuivi {
    id?: string;
    contenu?: string;
    dateProchaineRdv?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    prospectId?: string;
    suiviId?: string;
}

export class CompteRenduSuivi implements ICompteRenduSuivi {
    constructor(
        public id?: string,
        public contenu?: string,
        public dateProchaineRdv?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public prospectId?: string,
        public suiviId?: string
    ) {}
}
