import { ISuivi } from 'app/shared/model//suivi.model';
import { IUser } from 'app/core/user/user.model';

export interface IProspect {
    id?: string;
    nom?: string;
    email?: string;
    telephone?: string;
    type?: number;
    dateRdv?: string;
    compteRendu?: string;
    localisation?: string;
    position?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    userId?: string;
    suivi?: ISuivi;
    user?: IUser;
}

export class Prospect implements IProspect {
    constructor(
        public id?: string,
        public nom?: string,
        public email?: string,
        public telephone?: string,
        public type?: number,
        public dateRdv?: string,
        public compteRendu?: string,
        public localisation?: string,
        public position?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public userId?: string,
        public suivi?: ISuivi,
        public user?: IUser
    ) {}
}
