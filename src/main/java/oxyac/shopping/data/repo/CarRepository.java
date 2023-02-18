package oxyac.shopping.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.entity.Website;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends PagingAndSortingRepository<Car, Long>, JpaRepository<Car, Long> {
    @Query("select c from Car c where c.website.id = ?1")
    Page<Car> findByWebsite_Id(Long id, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Car c where c.foreignId < ?1 and c.website = ?2 ")
    void deleteByForeignIDLessThanAndWebsite(Long foreignId, Website website);

    @Query("SELECT c.foreignId from Car c where c.website = ?1 order by c.foreignId DESC LIMIT 1 OFFSET 500")
    Optional<Long> findByWebsiteOrderByForeignIdDesc(Website website);

    @Query("select c.foreignId from Car c where c.website = ?1")
    Optional<List<Long>> getForeignIdsByWebsite(Website website);
}
