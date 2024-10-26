@SpringBootTest
    @AutoConfigureMockMvc
    public class BookControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testCreateBook() throws Exception {
            String bookJson = "{\"title\": \"Effective Java\", \"author\": \"Joshua Bloch\", \"isbn\": \"9780134685991\", \"available\": true}";

            mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("Effective Java"));
        }

        @Test
        public void testGetAllBooks() throws Exception {
            mockMvc.perform(get("/api/books"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }