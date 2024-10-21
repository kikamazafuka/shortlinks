package lt.arts.shortlinks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * The Url entity class represents a URL object in the database.
 * It contains fields for the original URL and its shortened version.
 */

@Data
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Original URL cannot be empty")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
    private String originalUrl;
    private String shortenedUrl;

}
