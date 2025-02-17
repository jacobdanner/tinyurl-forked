/* (C)2024 */
package com.jwang.shortener.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("urlmap")
public class UrlEntity {
    @Id private String hash;
    private String originalUrl;
    private LocalDate expireDate;
    private String username;
}
