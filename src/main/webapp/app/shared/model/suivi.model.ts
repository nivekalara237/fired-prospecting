import { IUser } from 'app/core/user/user.model';

export interface ISuivi {
    id?: string;
    dateRdv?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    user?: IUser;
}

export class Suivi implements ISuivi {
    constructor(
        public id?: string,
        public dateRdv?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public user?: IUser
    ) {}
}
