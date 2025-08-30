package org.jinwenxin.lifewishlist.service;

import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.User;

import java.util.List;
import java.util.Optional;

public interface BucketService {
    Bucket createBucket(Bucket bucket);
    Bucket updateBucket(Bucket bucket);
    void deleteBucket(Long bucketId);
    Optional<Bucket> getBucketById(Long bucketId);
    List<Bucket> getBucketsByUser(User user);
    List<Bucket> getPublicBucketsByUser(User user);
    boolean isBucketOwnedByUser(Long bucketId, Long userId);
}