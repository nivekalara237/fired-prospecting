export interface IFichier {
    id?: string;
    path?: string;
    type?: string;
    size?: number;
    model?: string;
    modelId?: string;
    createdAt?: string;
    updatedAt?: string;
    deletedAt?: string;
}

export class Fichier implements IFichier {
    constructor(
        public id?: string,
        public path?: string,
        public type?: string,
        public size?: number,
        public model?: string,
        public modelId?: string,
        public createdAt?: string,
        public updatedAt?: string,
        public deletedAt?: string
    ) {}
}
