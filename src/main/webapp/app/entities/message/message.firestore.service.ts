import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/firestore';
import { IChannel } from '../../shared/model/channel.model';
import { IEntreprise } from '../../shared/model/entreprise.model';
import { IMessage, Message } from '../../shared/model/message.model';
import { Query } from '@firebase/firestore-types';

@Injectable({ providedIn: 'root' })
export class MessageFirestoreService {
    constructor(protected firestore: AngularFirestore) {}

    get(entreprise: IEntreprise, channel: IChannel) {
        const path = `enterprise_${entreprise.id}/${channel.id}`;
        return this.firestore
            .collection(`enterprise_${entreprise.id}`)
            .doc(`channel_${channel.id}`)
            .collection(`messages`, ref => ref.orderBy('time', 'asc').limit(2000))
            .snapshotChanges();
    }

    paginate(entreprise: IEntreprise, channel: IChannel, startKey: string) {
        const path = `enterprise_${entreprise.id}/${channel.id}`;
        return this.firestore
            .collection(`entreprise_${entreprise.id}`)
            .doc(`channel_${channel.id}`)
            .collection('messages', function(ref) {
                return ref
                    .limit(5)
                    .endAt(startKey)
                    .orderBy('time', 'asc');
                //.
            })
            .get();
    }
    create(entreprise: IEntreprise, channel: IChannel, message: Message) {
        const path = `enterprise_${entreprise.id}/${channel.id}`;
        let key = this.firestore.createId();
        message.key = key;
        //console.log("MESSAGE",Object.assign({},message));
        return this.firestore
            .collection(`enterprise_${entreprise.id}`)
            .doc(`channel_${channel.id}`)
            .collection(`messages`)
            .doc(key)
            .set(Object.assign({}, message));
    }

    update(entreprise: IEntreprise, channel: IChannel, message: IMessage) {
        const path = `enterprise_${entreprise.id}/${channel.id}`;
        delete message.id;
        this.firestore.doc(path + '/' + message.id).update(message);
    }

    remove(entreprise: IEntreprise, channel: IChannel, mId: string) {
        const path = `enterprise_${entreprise.id}/${channel.id}`;
        this.firestore.doc(path + '/' + mId).delete();
    }
}
