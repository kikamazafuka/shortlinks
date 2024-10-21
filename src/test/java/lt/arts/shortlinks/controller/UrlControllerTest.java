package lt.arts.shortlinks.controller;

import lt.arts.shortlinks.model.Url;
import lt.arts.shortlinks.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    private static final String TEST_ORIGINAL_URL = "https://example.com";
    private static final String TEST_SHORTENED_URL = "https://short.ly/abc123";

    @Test
    void testCreateShortenedUrlSuccess() throws Exception {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);

        when(urlService.saveShortenedUrl(TEST_ORIGINAL_URL)).thenReturn(url);

        mockMvc.perform(post("/api/shortenUrl/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\":\"" + TEST_ORIGINAL_URL + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateShortenedUrlInvalidUrl() throws Exception {
        mockMvc.perform(post("/api/shortenUrl/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\":\"\"}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testGetShortenedUrlSuccess() throws Exception {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);
        url.setShortenedUrl(TEST_SHORTENED_URL);

        when(urlService.getShortenedUrl(TEST_ORIGINAL_URL)).thenReturn(Optional.of(url));

        mockMvc.perform(get("/api/shortenUrl/short")
                        .param("originalUrl", TEST_ORIGINAL_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetShortenedUrlNotFound() throws Exception {
        when(urlService.getShortenedUrl(TEST_ORIGINAL_URL)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/shortenUrl/short")
                        .param("originalUrl", TEST_ORIGINAL_URL))
                .andExpect(status().isNotFound());
    }
}
