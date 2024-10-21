package lt.arts.shortlinks.service;

import lt.arts.shortlinks.dao.UrlDao;
import lt.arts.shortlinks.model.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    private static final String TEST_ORIGINAL_URL = "https://example.com";
    private static final String TEST_SHORTENED_URL = "https://short.ly/abc123";

    @Mock
    private UrlDao urlDao;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSaveShortenedUrlShouldReturnNonNull() {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);
        url.setShortenedUrl(TEST_SHORTENED_URL);

        when(urlDao.save(any(Url.class))).thenReturn(url);

        Url result = urlService.saveShortenedUrl(TEST_ORIGINAL_URL);

        assertNotNull(result);
    }

    @Test
    void testSaveShortenedUrlShouldStartWithDomain() {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);
        url.setShortenedUrl(TEST_SHORTENED_URL);

        when(urlDao.save(any(Url.class))).thenReturn(url);

        Url result = urlService.saveShortenedUrl(TEST_ORIGINAL_URL);

        assertTrue(result.getShortenedUrl().startsWith("https://short.ly/"));
    }

    @Test
    void testSaveShortenedUrlShouldInvokeSaveMethodOnce() {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);
        url.setShortenedUrl(TEST_SHORTENED_URL);

        when(urlDao.save(any(Url.class))).thenReturn(url);

        urlService.saveShortenedUrl(TEST_ORIGINAL_URL);

        verify(urlDao, times(1)).save(any(Url.class));
    }

    @Test
    void testGenerateShortenUrlShouldReturnNonNull() {
        String shortenedUrl = urlService.generateShortenUrl();

        assertNotNull(shortenedUrl, "The shortened URL should not be null");
    }

    @Test
    void testGenerateShortenUrlShouldStartWithDomain() {
        String shortenedUrl = urlService.generateShortenUrl();

        assertTrue(shortenedUrl.startsWith("https://short.ly/"),
                "The shortened URL should start with the domain https://short.ly/");
    }

    @Test
    void testGenerateShortenUrlShouldHaveCorrectLength() {
        String shortenedUrl = urlService.generateShortenUrl();

        assertEquals(23, shortenedUrl.length(),
                "The length of the shortened URL should be 23 characters (17 domain + 6 postfix)");
    }

    @Test
    void testGenerateUrlPostfixShouldReturnNonNull() {
        String postfix = urlService.generateUrlPostfix();

        assertNotNull(postfix, "The generated postfix should not be null");
    }

    @Test
    void testGenerateUrlPostfixShouldHaveCorrectLength() {
        String postfix = urlService.generateUrlPostfix();

        assertEquals(6, postfix.length(), "The length of the generated postfix should be 6 characters");
    }

    @Test
    void testGenerateUrlPostfixShouldBeAlphanumeric() {
        String postfix = urlService.generateUrlPostfix();

        assertTrue(postfix.matches("[A-Za-z0-9]+"), "The generated postfix should contain only alphanumeric characters");
    }


    @Test
    void testGetShortenedUrlShouldReturnPresentWhenUrlExists() {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);
        url.setShortenedUrl(TEST_SHORTENED_URL);

        when(urlDao.findFirstByOriginalUrl(TEST_ORIGINAL_URL)).thenReturn(Optional.of(url));

        Optional<Url> result = urlService.getShortenedUrl(TEST_ORIGINAL_URL);

        assertTrue(result.isPresent(), "The result should be present when the original URL exists");
    }

    @Test
    void testGetShortenedUrlShouldReturnCorrectShortenedUrl() {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);
        url.setShortenedUrl(TEST_SHORTENED_URL);

        when(urlDao.findFirstByOriginalUrl(TEST_ORIGINAL_URL)).thenReturn(Optional.of(url));

        Optional<Url> result = urlService.getShortenedUrl(TEST_ORIGINAL_URL);

        assertEquals(TEST_SHORTENED_URL, result.get().getShortenedUrl(),
                "The shortened URL returned should match the expected shortened URL");
    }

    @Test
    void testGetShortenedUrlShouldInvokeDaoMethodOnce() {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);
        url.setShortenedUrl(TEST_SHORTENED_URL);

        when(urlDao.findFirstByOriginalUrl(TEST_ORIGINAL_URL)).thenReturn(Optional.of(url));

        urlService.getShortenedUrl(TEST_ORIGINAL_URL);

        verify(urlDao, times(1)).findFirstByOriginalUrl(TEST_ORIGINAL_URL);
    }

}