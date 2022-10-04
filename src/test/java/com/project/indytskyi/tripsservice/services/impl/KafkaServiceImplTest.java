package com.project.indytskyi.tripsservice.services.impl;

//@SpringBootTest()
//@Testcontainers

import com.project.indytskyi.tripsservice.TripsServiceApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@Import(KafkaServiceImplTest.KafkaTestContainersConfiguration.class)
@SpringBootTest(classes = TripsServiceApplication.class)
@DirtiesContext
class KafkaServiceImplTest {

//
//    @ClassRule
//    public static KafkaContainer kafka =
//            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
//
//
//    @Autowired
//    private KafkaServiceImpl producer;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//
//    }
//
//    @Test
//    public void testCarKafka() {
//        TripFinishDto tripFinishDto = createTripFinishDto();
//        producer.sendOrderToCarService(tripFinishDto);
//
//    }
//
//
//    @Test
//    public void testBackofficeKafka() {
//        TrafficOrderEntity trafficOrder = createTrafficOrder();
//        producer.sendOrderToBackOfficeService(trafficOrder, 200);
//
//    }
//
//
//    @TestConfiguration
//    static class KafkaTestContainersConfiguration {
//
//
//
//        @Bean
//        public ProducerFactory<String, String> producerFactory() {
//            Map<String, Object> configProps = new HashMap<>();
//            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
//            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//            return new DefaultKafkaProducerFactory<>(configProps);
//        }
//
//        @Bean
//        public KafkaTemplate<String, String> kafkaTemplate() {
//            return new KafkaTemplate<>(producerFactory());
//        }
//
//    }

}
