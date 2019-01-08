import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FireDRapportModule } from './rapport/rapport.module';
import { FireDProspectModule } from './prospect/prospect.module';
import { FireDCopieModule } from './copie/copie.module';
import { FireDObjetModule } from './objet/objet.module';
import { FireDChannelModule } from './channel/channel.module';
import { FireDEntrepriseModule } from './entreprise/entreprise.module';
import { FireDMessageModule } from './message/message.module';
import { FireDSuiviModule } from './suivi/suivi.module';
import { FireDCompteRenduSuiviModule } from './compte-rendu-suivi/compte-rendu-suivi.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        FireDRapportModule,
        FireDProspectModule,
        FireDCopieModule,
        FireDObjetModule,
        FireDChannelModule,
        FireDEntrepriseModule,
        FireDMessageModule,
        FireDSuiviModule,
        FireDCompteRenduSuiviModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FireDEntityModule {}
