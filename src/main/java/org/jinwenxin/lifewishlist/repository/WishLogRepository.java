package org.jinwenxin.lifewishlist.repository;

import org.jinwenxin.lifewishlist.model.Wish;
import org.jinwenxin.lifewishlist.model.WishLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishLogRepository extends JpaRepository<WishLog, Long> {
    List<WishLog> findByWish(Wish wish);
}