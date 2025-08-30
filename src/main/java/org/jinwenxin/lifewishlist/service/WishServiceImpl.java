package org.jinwenxin.lifewishlist.service;

import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.Wish;
import org.jinwenxin.lifewishlist.model.Wish.WishStatus;
import org.jinwenxin.lifewishlist.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishServiceImpl implements WishService {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private BucketService bucketService;

    @Override
    public Wish createWish(Wish wish) {
        return wishRepository.save(wish);
    }

    @Override
    public Wish updateWish(Wish wish) {
        return wishRepository.save(wish);
    }

    @Override
    public void deleteWish(Long wishId) {
        wishRepository.deleteById(wishId);
    }

    @Override
    public Optional<Wish> getWishById(Long wishId) {
        return wishRepository.findById(wishId);
    }

    @Override
    public List<Wish> getWishesByBucket(Bucket bucket) {
        return wishRepository.findByBucket(bucket);
    }

    @Override
    public List<Wish> getWishesByBucketAndStatus(Bucket bucket, WishStatus status) {
        return wishRepository.findByBucketAndStatus(bucket, status);
    }

    @Override
    public Long countWishesByBucket(Bucket bucket) {
        return wishRepository.countByBucket(bucket);
    }

    @Override
    public Long countWishesByBucketAndStatus(Bucket bucket, WishStatus status) {
        return wishRepository.countByBucketAndStatus(bucket, status);
    }

    @Override
    public boolean isWishOwnedByUser(Long wishId, Long userId) {
        Optional<Wish> wish = wishRepository.findById(wishId);
        if (wish.isPresent()) {
            Bucket bucket = wish.get().getBucket();
            return bucketService.isBucketOwnedByUser(bucket.getId(), userId);
        }
        return false;
    }
}