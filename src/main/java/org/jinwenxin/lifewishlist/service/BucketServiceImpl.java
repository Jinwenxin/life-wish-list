package org.jinwenxin.lifewishlist.service;

import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.User;
import org.jinwenxin.lifewishlist.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BucketServiceImpl implements BucketService {

    @Autowired
    private BucketRepository bucketRepository;

    @Override
    public Bucket createBucket(Bucket bucket) {
        return bucketRepository.save(bucket);
    }

    @Override
    public Bucket updateBucket(Bucket bucket) {
        return bucketRepository.save(bucket);
    }

    @Override
    public void deleteBucket(Long bucketId) {
        bucketRepository.deleteById(bucketId);
    }

    @Override
    public Optional<Bucket> getBucketById(Long bucketId) {
        return bucketRepository.findById(bucketId);
    }

    @Override
    public List<Bucket> getBucketsByUser(User user) {
        return bucketRepository.findByUser(user);
    }

    @Override
    public List<Bucket> getPublicBucketsByUser(User user) {
        return bucketRepository.findByUserAndIsPublic(user, true);
    }

    @Override
    public boolean isBucketOwnedByUser(Long bucketId, Long userId) {
        Optional<Bucket> bucket = bucketRepository.findById(bucketId);
        return bucket.isPresent() && bucket.get().getUser().getId().equals(userId);
    }
}