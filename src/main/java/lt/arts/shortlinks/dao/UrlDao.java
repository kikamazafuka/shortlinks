package lt.arts.shortlinks.dao;

import lt.arts.shortlinks.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlDao extends JpaRepository<Url, Long> {
    Optional<Url> findFirstByOriginalUrl(String originalUrl);

}
