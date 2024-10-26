package lt.arts.shortlinks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UrlDto {
    @NotBlank(message = "Original URL cannot be empty")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
    private String originalUrl;
    private String shortenedUrl;
}
