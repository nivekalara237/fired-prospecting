package com.niveka.config.dbmigrations;

import com.niveka.domain.Authority;
import com.niveka.domain.Entreprise;
import com.niveka.domain.User;
import com.niveka.security.AuthoritiesConstants;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.niveka.web.rest.util.Utils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        Authority comAuthority = new Authority();
        comAuthority.setName(AuthoritiesConstants.COMMERCIAL);
        Authority ambasAuthority = new Authority();
        ambasAuthority.setName(AuthoritiesConstants.AMBASSADOR);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
        mongoTemplate.save(comAuthority);
        mongoTemplate.save(ambasAuthority);
    }

    @ChangeSet(order = "02", author = "initiator", id="02-addEnterprise")
    public void addEnterprise(MongoTemplate mongoTemplate){
        Entreprise entreprise = new Entreprise();
        entreprise.setId("niveka-com-dev-team");
        entreprise.designation(AuthoritiesConstants.ENTERPRISE);
        entreprise.setCreatedAt(Utils.currentJodaDateStr());
        entreprise.setLogo("https://nivekaa.com/img/logo.png");
        entreprise.setNombre_utilisteur(5);
        entreprise.setRange_utilisateur("0-5");
        mongoTemplate.save(entreprise);
    }

    @ChangeSet(order = "03", author = "initiator", id = "03-addUsers")
    public void addUsers(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        Authority comAuthority = new Authority();
        comAuthority.setName(AuthoritiesConstants.COMMERCIAL);
        Authority ambasAuthority = new Authority();
        ambasAuthority.setName(AuthoritiesConstants.AMBASSADOR);

        Entreprise entreprise = new Entreprise();
        entreprise.setId("niveka-com-dev-team");
        entreprise.designation(AuthoritiesConstants.ENTERPRISE);
        entreprise.setCreatedAt(Utils.currentJodaDateStr());
        entreprise.setLogo("https://nivekaa.com/img/logo.png");
        entreprise.setNombre_utilisteur(5);
        entreprise.setRange_utilisateur("0-5");

        User systemUser = new User();
        systemUser.setId("user-0");
        systemUser.setLogin("system");
        systemUser.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
        systemUser.setFirstName("");
        systemUser.setLastName("System");
        systemUser.setEmail("system@localhost");
        systemUser.setActivated(true);
        systemUser.setLangKey("fr");
        systemUser.setEntreprise(entreprise);
        systemUser.setCreatedBy(systemUser.getLogin());
        systemUser.setCreatedDate(Instant.now());
        systemUser.getAuthorities().add(adminAuthority);
        systemUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(systemUser);

        User anonymousUser = new User();
        anonymousUser.setId("user-1");
        anonymousUser.setLogin("anonymoususer");
        anonymousUser.setPassword("$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO");
        anonymousUser.setFirstName("Anonymous");
        anonymousUser.setLastName("User");
        anonymousUser.setEmail("anonymous@localhost");
        anonymousUser.setActivated(true);
        anonymousUser.setLangKey("fr");
        anonymousUser.setCreatedBy(systemUser.getLogin());
        anonymousUser.setCreatedDate(Instant.now());
        anonymousUser.setEntreprise(entreprise);
        mongoTemplate.save(anonymousUser);

        User adminUser = new User();
        adminUser.setId("user-2");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@nivekaa.com");
        adminUser.setActivated(true);
        adminUser.setLangKey("fr");
        adminUser.setEntreprise(entreprise);
        adminUser.setCreatedBy(systemUser.getLogin());
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(adminUser);

        User userUser = new User();
        userUser.setId("user-3");
        userUser.setLogin("commercial");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("joe");
        userUser.setLastName("Commercial");
        userUser.setEmail("commercial@nivekaa.com");
        userUser.setActivated(true);
        userUser.setLangKey("fr");
        userUser.setEntreprise(entreprise);
        userUser.setCreatedBy(systemUser.getLogin());
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(comAuthority);
        mongoTemplate.save(userUser);

        User ambasUser = new User();
        ambasUser.setId("ambassador");
        ambasUser.setLogin("ambassador");
        ambasUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        ambasUser.setFirstName("john");
        ambasUser.setLastName("Ambassador");
        ambasUser.setEmail("ambassador@nivekaa.com");
        ambasUser.setActivated(true);
        ambasUser.setLangKey("fr");
        ambasUser.setEntreprise(entreprise);
        ambasUser.setCreatedBy(systemUser.getLogin());
        ambasUser.setCreatedDate(Instant.now());
        ambasUser.getAuthorities().add(comAuthority);
        mongoTemplate.save(ambasUser);
    }
}
