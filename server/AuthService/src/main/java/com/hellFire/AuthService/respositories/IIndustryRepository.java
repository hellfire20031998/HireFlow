package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IIndustryRepository extends JpaRepository<Industry, Long> {

    List<Industry> findTop10ByDeletedFalseOrderByCreatedAtDesc();

    @Query("SELECT i FROM Industry i WHERE i.deleted = false AND (LOWER(i.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(i.code) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<Industry> search(@Param("q") String q);
}
