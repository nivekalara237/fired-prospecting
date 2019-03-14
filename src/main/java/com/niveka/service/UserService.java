package com.niveka.service;

import com.mongodb.client.result.UpdateResult;
import com.niveka.config.Constants;
import com.niveka.domain.Authority;
import com.niveka.domain.Entreprise;
import com.niveka.domain.User;
import com.niveka.repository.AuthorityRepository;
import com.niveka.repository.UserRepository;
import com.niveka.repository.search.UserSearchRepository;
import com.niveka.security.AuthoritiesConstants;
import com.niveka.security.SecurityUtils;
import com.niveka.service.dto.UserDTO;
import com.niveka.service.util.RandomUtil;
import com.niveka.web.rest.errors.EmailAlreadyUsedException;
import com.niveka.web.rest.errors.InvalidPasswordException;
import com.niveka.web.rest.errors.LoginAlreadyUsedException;
import com.niveka.web.rest.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserSearchRepository userSearchRepository;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    @Autowired
    private MongoTemplate mongoTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSearchRepository userSearchRepository, AuthorityRepository authorityRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSearchRepository = userSearchRepository;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                user.setActivatedAt(Utils.currentJodaDateStr());
                user.setUpdatedAt(Utils.currentJodaDateStr());
                userRepository.save(user);
                //userSearchRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                userRepository.save(user);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                user.setUpdatedAt(Utils.currentJodaDateStr());
                userRepository.save(user);
                this.clearUserCaches(user);
                return user;
            });
    }
    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new LoginAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        newUser.setCreatedAt(Utils.currentJodaDateStr());
        newUser.setUpdatedAt(Utils.currentJodaDateStr());
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.COMMERCIAL).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        newUser.setEntreprise(userDTO.getEntreprise());
        newUser.setEntrepriseId(userDTO.getEntrepriseId());
        newUser.setIosFcmToken(userDTO.getIosFcmToken());
        newUser.setWebFcmToken(userDTO.getWebFcmToken());
        newUser.setAndroidFcmToken(userDTO.getAndroidFcmToken());
        userRepository.save(newUser);
        userSearchRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser){
        if (existingUser.getActivated()) {
             return false;
        }
        userRepository.delete(existingUser);
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        user.setActivatedAt(Utils.currentJodaDateStr());
        user.setUpdatedAt(Utils.currentJodaDateStr());
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        if (userDTO.getEntreprise()!=null){
            user.setEntreprise(userDTO.getEntreprise());
        }

        if (userDTO.getAndroidFcmToken()!=null)
            user.setAndroidFcmToken(userDTO.getAndroidFcmToken());

        if (userDTO.getWebFcmToken()!=null)
            user.setWebFcmToken(userDTO.getWebFcmToken());

        if (userDTO.getIosFcmToken()!=null)
            user.setIosFcmToken(userDTO.getAndroidFcmToken());
        userRepository.save(user);
        userSearchRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl,String entrepriseId) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email.toLowerCase());
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                user.setEntrepriseId(entrepriseId);
                user.setUpdatedAt(Utils.currentJodaDateStr());
                userRepository.save(user);
                userSearchRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail().toLowerCase());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                user.setUpdatedAt(Utils.currentJodaDateStr());
                user.setEntrepriseId(userDTO.getEntrepriseId());
                if (userDTO.getAndroidFcmToken()!=null)
                    user.setAndroidFcmToken(userDTO.getAndroidFcmToken());

                if (userDTO.getWebFcmToken()!=null)
                    user.setWebFcmToken(userDTO.getWebFcmToken());

                if (userDTO.getIosFcmToken()!=null)
                    user.setIosFcmToken(userDTO.getAndroidFcmToken());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                userRepository.save(user);
                userSearchRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            userSearchRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                user.setUpdatedAt(Utils.currentJodaDateStr());
                userRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    public List<User> getCommercials(){
        final List<User>[] commerciaux = new List[]{new ArrayList<>()};
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user->{
                commerciaux[0] = userRepository.findAllByEntrepriseId(user.getEntrepriseId());
                //log.debug("USER@@@@@@@@ {}",commerciaux[0]);
                //log.debug("ENTREPRISE_ID@@@@@@@@@@@@ {}",user.getEntrepriseId());
            });
        return commerciaux.length!=0?commerciaux[0]:new ArrayList<>();
    }//aclave 1g;otipasse

    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public Optional<User> getUserWithAuthorities(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                userSearchRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }

    public User findOne(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public Entreprise findEntreprise(String id){
        Query query = new Query();

        log.debug("ENT_: {}", SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin));
        //Query query = new Query(where("user.$id").is(user.getId()));
        //Entreprise e = mongoTemplate.findOne(query, Entreprise.class);
        return null;
    }

    public boolean updateToken(String userId,String token,String type){
        Query query1 = new Query(Criteria.where("id").is(userId));
        Update update1 = new Update();
        if (type.equals("web"))
            update1.set("web_fcm_token", token);
        else if (type.equals("ios"))
            update1.set("ios_fcm_token",token);
        else
            update1.set("android_fcm_token",token);
        UpdateResult res = mongoTemplate.updateFirst(query1, update1, User.class);
        return res.getModifiedCount()>0;
    }
}
