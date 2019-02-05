export interface IRapport {
    id?: string;
    objet?: string;
    copies?: string;
    contenu?: string;
    type?: number;
    position?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    userId?: string;
    seen?: boolean;
}

export class Rapport implements IRapport {
    constructor(
        public id?: string,
        public objet?: string,
        public copies?: string,
        public contenu?: string,
        public type?: number,
        public position?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public userId?: string,
        public seen?: boolean
    ) {}
}
