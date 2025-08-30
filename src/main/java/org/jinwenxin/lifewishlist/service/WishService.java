package org.jinwenxin.lifewishlist.service;

import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.Wish;
import org.jinwenxin.lifewishlist.model.Wish.WishStatus;

import java.util.List;
import java.util.Optional;

public interface WishService {
    Wish createWish(Wish wish);
    Wish updateWish(Wish wish);
    void deleteWish(Long wishId);
    Optional<Wish> getWishById(Long wishId);
    List<Wish> getWishesByBucket(Bucket bucket);
    List<Wish> getWishesByBucketAndStatus(Bucket bucket, WishStatus status);
    Long countWishesByBucket(Bucket bucket);
    Long countWishesByBucketAndStatus(Bucket bucket, WishStatus status);
    boolean isWishOwnedByUser(Long wishId, Long userId);
}