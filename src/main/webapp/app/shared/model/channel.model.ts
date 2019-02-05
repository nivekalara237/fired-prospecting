import { IUser } from 'app/core/user/user.model';

export interface IChannel {
    id?: string;
    designation?: string;
    entrepriseId?: string;
    code?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    users?: IUser[];
}

export class Channel implements IChannel {
    constructor(
        public id?: string,
        public designation?: string,
        public entrepriseId?: string,
        public code?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public users?: IUser[]
    ) {}
}
