import { IUser } from '../../core/user/user.model';
export interface IMessage {
    id?: string;
    contenu?: string;
    key?: string;
    vu?: boolean;
    time?: string;
    channelId?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    userId?: string;
    user?: IUser;
}

export class Message implements IMessage {
    constructor(
        public id?: string,
        public contenu?: string,
        public key?: string,
        public vu?: boolean,
        public time?: string,
        public channelId?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public userId?: string,
        public user?: IUser
    ) {}
}
