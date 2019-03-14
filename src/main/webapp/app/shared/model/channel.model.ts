import { IUser } from 'app/core/user/user.model';
import { IEntreprise } from './entreprise.model';

export interface IChannel {
    id?: string;
    designation?: string;
    code?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    users?: IUser[];
    entreprise?: IEntreprise;
    entrepriseId?: string;
}

export class Channel implements IChannel {
    constructor(
        public id?: string,
        public designation?: string,
        public code?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public users?: IUser[],
        public entreprise?: IEntreprise,
        public entrepriseId?: string
    ) {}
}
