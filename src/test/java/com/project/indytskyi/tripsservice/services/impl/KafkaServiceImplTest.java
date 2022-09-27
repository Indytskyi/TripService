package com.project.indytskyi.tripsservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

//@SpringBootTest()
//@Testcontainers
@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
class KafkaServiceImplTest {

//    @Container
//    static KafkaContainer kafka = new KafkaContainer(
//            DockerImageName.parse("confluentinc/cp-kafka:latest")
//    );
//
//    @BeforeAll
//    static void beforeAll() {
//        kafka.start();
//    }
//
//    @Autowired
//    KafkaService kafkaService;
//
//    @Autowired
//    KafkaAdmin admin;
//
//
//
//    private String kafkaCarTopic;
//
//    @Test
//    public void testSendInformationToCarTopic() {
//
//    }
//
//    @DynamicPropertySource
//    public static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.kafka.properties.bootstrap.servers",kafka::getBootstrapServers);
//    }
//
//    @SneakyThrows
//    @Test
//    public void testCreationOfTopicAtStartup()  {
//        AdminClient client = AdminClient.create(admin.getConfigurationProperties());
//        Collection<TopicListing> topicList = client.listTopics().listings().get();
//        assertNotNull(topicList);
//        assertEquals(topicList.stream().map(l -> l.name()).collect(Collectors.toList()), Arrays.asList("create-employee-events","springboot-topic"));
//    }





    //    @Container
//    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
//
//    @DynamicPropertySource
//    static void kafkaProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
//    }
//
//    @Autowired
//    private KafkaCarProducerConfig kafkaCarProducerConfig;
//
//    @MockBean
//    private KafkaServiceImpl kafkaService;
//
//    @Test
//    void test1() {
//        ArgumentCaptor<TripFinishDto> captor =
//                ArgumentCaptor.forClass(TripFinishDto.class);
//
//        CarUpdateInfoAfterTripDto carUpdate = createCarUpdateDto();
//        TripFinishDto tripFinishDto = createTripFinishDto();
//
//        kafkaService.sendOrderToCarService(tripFinishDto);
//
//        verify(kafkaService, times(1))
//                .sendOrderToCarService(captor.capture());
//        assertNotNull(captor.getValue());
//    }

}
