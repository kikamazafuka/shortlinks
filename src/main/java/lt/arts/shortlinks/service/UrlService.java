package lt.arts.shortlinks.service;

import lt.arts.shortlinks.dao.UrlDao;
import lt.arts.shortlinks.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Service class responsible for managing the URL shortening operations.
 * This class provides methods for creating shortened URLs, generating URL postfixes,
 * and retrieving URLs from the database.
 */

@Service
public class UrlService {

    private static final String DOMAIN = "https://short.ly/";
    private final UrlDao urlDao;

    @Autowired
    public UrlService(UrlDao urlDao) {
        this.urlDao = urlDao;
    }

    public Url saveShortenedUrl(String originalUrl) {
        Url url = new Url();
        String shortenedUrl = generateShortenUrl();
        url.setOriginalUrl(originalUrl);
        url.setShortenedUrl(shortenedUrl);

        urlDao.save(url);

        return url;
    }

    public String generateShortenUrl() {
        return DOMAIN + generateUrlPostfix();
    }

    public String generateUrlPostfix() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder generatedPostfix = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            generatedPostfix.append(chars.charAt(random.nextInt(chars.length())));
        }
        return generatedPostfix.toString();
    }

    public Optional<Url> getShortenedUrl(String originalUrl) {
        return urlDao.findFirstByOriginalUrl(originalUrl);
    }
}
