import { IChannel } from 'app/shared/model//channel.model';
import { IUser } from 'app/core/user/user.model';

export interface IMessage {
    id?: string;
    contenu?: string;
    key?: string;
    vu?: string;
    time?: string;
    channelId?: number;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
    channel?: IChannel;
    user?: IUser;
}

export class Message implements IMessage {
    constructor(
        public id?: string,
        public contenu?: string,
        public key?: string,
        public vu?: string,
        public time?: string,
        public channelId?: number,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string,
        public channel?: IChannel,
        public user?: IUser
    ) {}
}
