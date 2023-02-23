package oxyac.shopping.data.repo;

import org.springframework.data.repository.CrudRepository;
import oxyac.shopping.data.entity.Website;

public interface WebsiteRepository extends CrudRepository<Website, Long> {
    Website findByUriToParse(String uriToParse);
}
