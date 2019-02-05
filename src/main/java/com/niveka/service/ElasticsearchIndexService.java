package com.niveka.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService {

    /*private static final Lock reindexLock = new ReentrantLock();

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final ZChannelRepository ZChannelRepository;

    private final ZChannelSearchRepository ZChannelSearchRepository;

    private final CompteRenduSuiviRepository compteRenduSuiviRepository;

    private final CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository;

    private final CopieRepository copieRepository;

    private final CopieSearchRepository copieSearchRepository;

    private final EntrepriseRepository entrepriseRepository;

    private final EntrepriseSearchRepository entrepriseSearchRepository;

    private final MessageRepository messageRepository;

    private final MessageSearchRepository messageSearchRepository;

    private final ObjetRepository objetRepository;

    private final ObjetSearchRepository objetSearchRepository;

    private final ProspectRepository prospectRepository;

    private final ProspectSearchRepository prospectSearchRepository;

    private final RapportRepository rapportRepository;

    private final RapportSearchRepository rapportSearchRepository;

    private final SuiviRepository suiviRepository;

    private final SuiviSearchRepository suiviSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        ZChannelRepository ZChannelRepository,
        ZChannelSearchRepository ZChannelSearchRepository,
        CompteRenduSuiviRepository compteRenduSuiviRepository,
        CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository,
        CopieRepository copieRepository,
        CopieSearchRepository copieSearchRepository,
        EntrepriseRepository entrepriseRepository,
        EntrepriseSearchRepository entrepriseSearchRepository,
        MessageRepository messageRepository,
        MessageSearchRepository messageSearchRepository,
        ObjetRepository objetRepository,
        ObjetSearchRepository objetSearchRepository,
        ProspectRepository prospectRepository,
        ProspectSearchRepository prospectSearchRepository,
        RapportRepository rapportRepository,
        RapportSearchRepository rapportSearchRepository,
        SuiviRepository suiviRepository,
        SuiviSearchRepository suiviSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.ZChannelRepository = ZChannelRepository;
        this.ZChannelSearchRepository = ZChannelSearchRepository;
        this.compteRenduSuiviRepository = compteRenduSuiviRepository;
        this.compteRenduSuiviSearchRepository = compteRenduSuiviSearchRepository;
        this.copieRepository = copieRepository;
        this.copieSearchRepository = copieSearchRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseSearchRepository = entrepriseSearchRepository;
        this.messageRepository = messageRepository;
        this.messageSearchRepository = messageSearchRepository;
        this.objetRepository = objetRepository;
        this.objetSearchRepository = objetSearchRepository;
        this.prospectRepository = prospectRepository;
        this.prospectSearchRepository = prospectSearchRepository;
        this.rapportRepository = rapportRepository;
        this.rapportSearchRepository = rapportSearchRepository;
        this.suiviRepository = suiviRepository;
        this.suiviSearchRepository = suiviSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }*/

    /*@Async
    @Timed
    public void reindexAll() {
        if (reindexLock.tryLock()) {
            try {
                reindexForClass(ZChannel.class, channelRepository, channelSearchRepository);
                reindexForClass(CompteRenduSuivi.class, compteRenduSuiviRepository, compteRenduSuiviSearchRepository);
                reindexForClass(Copie.class, copieRepository, copieSearchRepository);
                reindexForClass(Entreprise.class, entrepriseRepository, entrepriseSearchRepository);
                reindexForClass(Message.class, messageRepository, messageSearchRepository);
                reindexForClass(Objet.class, objetRepository, objetSearchRepository);
                reindexForClass(Prospect.class, prospectRepository, prospectSearchRepository);
                reindexForClass(Rapport.class, rapportRepository, rapportSearchRepository);
                reindexForClass(Suivi.class, suiviRepository, suiviSearchRepository);
                reindexForClass(User.class, userRepository, userSearchRepository);

                log.info("Elasticsearch: Successfully performed reindexing");
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: concurrent reindexing attempt");
        }
    }
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            // if a JHipster entity field is the owner side of a many-to-many relationship, it should be loaded manually
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(ManyToMany.class) != null)
                .filter(field -> field.getAnnotation(ManyToMany.class).mappedBy().isEmpty())
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            entityClass.getSimpleName(), field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            int size = 100;
            for (int i = 0; i <= jpaRepository.count() / size; i++) {
                Pageable page = new PageRequest(i, size);
                log.info("Indexing page {} of {}, size {}", i, jpaRepository.count() / size, size);
                Page<T> results = jpaRepository.findAll(page);
                results.map(result -> {
                    // if there are any relationships to load, do it now
                    relationshipGetters.forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                elasticsearchRepository.save(results.getContent());
            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }*/
}
