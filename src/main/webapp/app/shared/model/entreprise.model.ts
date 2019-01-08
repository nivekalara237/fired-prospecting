import { IUser } from 'app/core/user/user.model';

export interface IEntreprise {
    id?: string;
    designation?: string;
    logo?: string;
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
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public users?: IUser[]
    ) {}
}
