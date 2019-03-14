package com.niveka.service.projetction;

import com.niveka.domain.Authority;
import com.niveka.domain.Entreprise;
import com.niveka.domain.Suivi;
import com.niveka.domain.User;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

/**
 *  Created by Nivek@lara on 21/02/2019.
 */

@Projection(name = "inlineEntreprise", types = { User.class })
public interface InlineEntreprise {
    String getId();
    String getLogin();
    String getPassword();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getImageUrl();
    boolean getActivated();
    String getActivationKey();
    String getResetKey();
    String getLangKey();
    Set<Authority> getAuthorities();
    Set<Suivi> getSuivis();
    String getUpdatedAt();
    String getActivatedAt();
    Entreprise getEntreprise();
}
