package com.project.indytskyi.tripsservice.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
class TrackControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TracksRepository tracksRepository;
//
//    @MockBean
//    private TrackServiceImpl trackService;
//
//
//
//
//    @BeforeEach
//    public void setup() {
//        reset();
//    }
//
//    @AfterEach
//    void tearDown() {
//        tracksRepository.deleteAll();
//    }
//
//
//    @DisplayName("Save coordinates that we get from GPS")
//    @SneakyThrows
//    @Test
//    void saveCurrentCoordinates() throws Exception {
//        final CurrentCoordinatesDto coordinatesDto = new CurrentCoordinatesDto(20, 20);
//        TrackEntity track = new TrackEntity();
//        track.setLatitude(20);
//        track.setLongitude(20);
//
//
//
//
//    }





//
//    //    @InjectMocks
////    private TrackController trackController;
//
//    @Autowired
//    private TrackServiceImpl trackService;
//
//    private JacksonTester<CurrentCoordinatesDto> jsonCurrentCoordinatesDTO;
//
////    @BeforeEach
////    public void setup() {
////        JacksonTester.initFields(this, new ObjectMapper());
////        mvc = MockMvcBuilders.standaloneSetup(trackController)
////                .build();
////    }
//
//    @Test
//    void getTrackOrder() throws Exception {
//        // given
//        TrackEntity track = new TrackEntity();
//        track.setLongitude(10);
//        track.setLatitude(10);
//        given(trackService.findOne(2))
//                .willReturn(track);
//
//        // when
//        MockHttpServletResponse response = mockMvc.perform(
//                        get("/trip/track/2")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
////        assertThat(response.getContentAsString()).isEqualTo(
////
////                jsonCurrentCoordinatesDTO.write(new CurrentCoordinatesDTO(10, 10)).getJson()
////        );
//
//    }
//
//    @Test
//    void getTrackOrder2() throws Exception {
//        // given
//        given(trackService.findOne(2))
//                .willThrow(new TrackNotFoundException());
//
//        // when
//        MockHttpServletResponse response = mockMvc.perform(
//                        get("/trip/track/2")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//        // then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
////        assertThat(response.getContentAsString()).isEqualTo(
////
////                jsonCurrentCoordinatesDTO.write(new CurrentCoordinatesDTO(10, 10)).getJson()
////        );
//
//    }
//
//    @DisplayName("Save coordinates that we get from GPS")
//    @SneakyThrows
//    @Test
//    void saveCurrentCoordinates() throws Exception {
//        CurrentCoordinatesDto coordinatesDto = new CurrentCoordinatesDto(20, 20);
//        TrackEntity track = new TrackEntity();
//        track.setLatitude(20);
//        track.setLongitude(20);
//        when(trackService.instanceTrack(coordinatesDto)).thenReturn(track);
//
////        MockHttpServletResponse response = mvc.perform(post("/trip/track/current")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(String.valueOf(coordinatesDto))
////                ).andReturn().getResponse();
////
////        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//
//                MockHttpServletResponse response = mockMvc.perform(
//                post("/trip/track/current").contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonCurrentCoordinatesDTO.
//                                write(new CurrentCoordinatesDto(20, 20))
//                                .getJson()))
//                .andReturn().getResponse();
//
//        // then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    @Test
//    void ifCurrentCoordinatesThatWeWantToSaveAreIncorrect() throws Exception {
//        MockHttpServletResponse response = mockMvc.perform(
//                        post("/trip/track/current").contentType(MediaType.APPLICATION_JSON)
//                                .content(jsonCurrentCoordinatesDTO.
//                                        write(new CurrentCoordinatesDto(-100,10))
//                                        .getJson()))
//                .andReturn().getResponse();
//
//        // then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }
}