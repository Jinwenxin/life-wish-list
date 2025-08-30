package org.jinwenxin.lifewishlist.repository;

import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByBucket(Bucket bucket);
    List<Wish> findByBucketAndStatus(Bucket bucket, Wish.WishStatus status);
    Long countByBucket(Bucket bucket);
    Long countByBucketAndStatus(Bucket bucket, Wish.WishStatus status);
}