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
    userId?: string;
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
        public userId?: string
    ) {}
}
