/**
 *  Created by Nivek@lara on 20/02/2019.
 */
import { Component, OnInit, OnDestroy, ChangeDetectorRef, Directive, EventEmitter } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import * as $ from 'jquery';

import { IMessage, Message } from 'app/shared/model/message.model';
import { AccountService } from 'app/core';
import { MessageService } from '../../entities/message/message.service';
import { ChannelService } from '../../entities/channel/channel.service';
import { IChannel } from '../../shared/model/channel.model';
import { IEntreprise } from '../../shared/model/entreprise.model';
import { MessageFirestoreService } from '../../entities/message/message.firestore.service';
import { MomentTimezoneModule } from 'angular-moment-timezone';
import { MomentModule } from 'ngx-moment';

@Component({
    selector: 'jhi-forum',
    templateUrl: './FCommercial.component.html',
    styleUrls: ['./FCommercial.component.css']
})
export class FCommercialComponent implements OnInit, OnDestroy {
    channels: IChannel[];
    channelsWithLastMessages: any[];
    messages: IMessage[];
    newMessage: Message = new Message();
    currentAccount: any;
    page: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    currentChannel: IChannel;
    entreprise: IEntreprise;

    constructor(
        protected messageService: MessageService,
        protected messageFirestoreService: MessageFirestoreService,
        protected channelService: ChannelService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.messages = [];
        this.page = 0;
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ message }) => {
            //console.log("xxx----xxx",message);
            this.newMessage = message;
        });

        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.entreprise = account.entreprise;
            //console.log("ENTREPRISE_USER",account.entreprise);
            if (account) {
                this.loadChannel();
            }
        });
    }

    ngOnDestroy() {
        //this.eventManager.destroy(this.eventSubscriber);
    }

    loadMessage() {
        this.messageFirestoreService.get(this.entreprise, this.currentChannel).subscribe(data => {
            this.messages = data.map(e => {
                //console.log("E",e);
                return {
                    //id: e.payload.doc.id,
                    ...e.payload.doc.data()
                } as IMessage;
            });
            var el = <HTMLElement>document.querySelector('.chat-body');
            el.scrollTop = el.scrollHeight;
            //console.log("MS",this.messages);
        });
    }

    loadChannel() {
        this.channelService.findForUser(this.currentAccount.id).subscribe(
            (res: HttpResponse<IChannel[]>) => {
                this.channels = res.body;
                //console.log("HHHHHHH---HHHH",this.channelsWithLastMessages.length)
                if (this.channels && this.channels.length > 0) {
                    this.currentChannel = this.channels[0];
                    this.channelSelected(this.currentChannel);
                    this.loadMessage();
                }
            },
            (res: HttpErrorResponse) => {
                console.log(res.message);
            }
        );
    }

    channelSelected(channel: IChannel) {
        //console.log("ID = ",id);
        document.querySelectorAll('.channelItem').forEach(function(i) {
            if (channel.id === i.id) {
                i.classList.add('active');
            } else i.classList.remove('active');
        });
        this.currentChannel = channel;
        this.getForum(channel);
    }

    getForum(channel: IChannel) {
        this.loadMessage();
    }

    setScrollTo(event) {
        let pos = $('.chat-body').scrollTop();
        if (pos == 0) {
            //alert('top of the div');
            /*let firstMessage:IMessage = this.messages[0];
            let keyFM:string = firstMessage.key;
            this.messageFirestoreService.paginate(this.entreprise,this.currentAccount,keyFM)
                .subscribe(data=>{
                    let ms:IMessage[] = data.map(e=>{
                        return {
                            ...e.payload.doc.data()
                        } as IMessage;
                    });

                    let lasts:IMessage[] = this.messages;
                });*/
        }
    }

    create(message: IMessage) {
        this.messageFirestoreService
            .create(this.entreprise, this.currentChannel, message)
            .then(data => {
                console.log(data);
                var el = <HTMLElement>document.querySelector('.chat-body');
                el.scrollTop = el.scrollHeight;
            })
            .catch(error => {
                console.error(error);
            });
    }

    update(message: IMessage) {
        this.messageFirestoreService.update(this.entreprise, this.currentChannel, message);
    }

    remove(id: string) {
        this.messageFirestoreService.remove(this.entreprise, this.currentChannel, id);
    }

    saveMessage() {
        let time = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString();
        time = time.replace('Z', '');
        const offset = new Date().getTimezoneOffset();
        let timezone = (offset < 0 ? '+' : '-') + this.pad(Math.abs(offset / 60), 2) + ':' + this.pad(Math.abs(offset % 60), 2);
        time = time.concat(timezone);

        this.newMessage.userId = this.currentAccount.id;
        this.newMessage.user = this.currentAccount;
        this.newMessage.channelId = this.currentChannel.id;
        this.newMessage.time = time;
        this.newMessage.createdAt = time;
        this.newMessage.vu = false;
        this.newMessage.deletedAt = '';
        this.newMessage.updatedAt = '';
        this.newMessage.id = '';
        this.create(this.newMessage);
        setTimeout(function() {
            //this.emptyNewMessage();
        }, 500);
        this.emptyNewMessage();
    }

    emptyNewMessage() {
        this.newMessage.userId = null;
        this.newMessage.user = null;
        this.newMessage.channelId = null;
        this.newMessage.time = null;
        this.newMessage.createdAt = null;
        this.newMessage.vu = false;
        this.newMessage.contenu = null;
    }

    pad(nbr: number, length: number): string {
        var str = '' + nbr;
        while (str.length < length) {
            str = '0' + str;
        }
        return str;
    }
}
