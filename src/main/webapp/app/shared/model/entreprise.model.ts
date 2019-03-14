import { IUser } from 'app/core/user/user.model';

export interface IEntreprise {
    id?: string;
    designation?: string;
    logo?: string;
    range_utilisateur?: string;
    nombre_utilisateur?: number;
    status?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    users?: IUser[];
}

export class Entreprise implements IEntreprise {
    constructor(
        public id?: string,
        public designation?: string,
        public logo?: string,
        public range_utilisateur?: string,
        public nombre_utilisateur?: number,
        public status?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public users?: IUser[]
    ) {}
}
