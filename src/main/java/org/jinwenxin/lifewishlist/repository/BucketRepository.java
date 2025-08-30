package org.jinwenxin.lifewishlist.repository;

import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    List<Bucket> findByUser(User user);
    List<Bucket> findByUserAndIsPublic(User user, Boolean isPublic);
}