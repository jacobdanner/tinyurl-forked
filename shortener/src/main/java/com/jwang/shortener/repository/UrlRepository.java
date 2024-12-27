/* (C)2024 */
package com.jwang.shortener.repository;

import com.jwang.shortener.model.UrlEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
    List<UrlEntity> findUrlEntitiesByUsername(String username);
}
