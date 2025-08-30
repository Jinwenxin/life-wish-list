package org.jinwenxin.lifewishlist.controller;

import org.jinwenxin.lifewishlist.dto.request.WishRequest;
import org.jinwenxin.lifewishlist.dto.response.WishResponse;
import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.Wish;
import org.jinwenxin.lifewishlist.service.BucketService;
import org.jinwenxin.lifewishlist.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buckets/{bucketId}/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @Autowired
    private BucketService bucketService;

    @PostMapping
    public ResponseEntity<?> createWish(@PathVariable Long bucketId, @Valid @RequestBody WishRequest wishRequest) {
        Optional<Bucket> bucketOptional = bucketService.getBucketById(bucketId);
        if (bucketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Wish wish = new Wish();
        wish.setTitle(wishRequest.getTitle());
        wish.setDescription(wishRequest.getDescription());
        wish.setStatus(Wish.WishStatus.NOT_STARTED);
        wish.setBucket(bucketOptional.get());

        Wish savedWish = wishService.createWish(wish);
        return ResponseEntity.ok(new WishResponse(savedWish));
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishesByBucket(@PathVariable Long bucketId) {
        Optional<Bucket> bucketOptional = bucketService.getBucketById(bucketId);
        if (bucketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Wish> wishes = wishService.getWishesByBucket(bucketOptional.get());
        List<WishResponse> wishResponses = wishes.stream()
                .map(WishResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(wishResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWishById(@PathVariable Long bucketId, @PathVariable Long id) {
        Optional<Wish> wishOptional = wishService.getWishById(id);
        if (wishOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Wish wish = wishOptional.get();
        // 检查是否属于指定的Bucket
        if (!wish.getBucket().getId().equals(bucketId)) {
            return ResponseEntity.badRequest().body("Wish does not belong to this bucket");
        }

        return ResponseEntity.ok(new WishResponse(wish));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWish(@PathVariable Long bucketId, @PathVariable Long id, @Valid @RequestBody WishRequest wishRequest) {
        Optional<Wish> wishOptional = wishService.getWishById(id);
        if (wishOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Wish wish = wishOptional.get();
        // 检查是否属于指定的Bucket
        if (!wish.getBucket().getId().equals(bucketId)) {
            return ResponseEntity.badRequest().body("Wish does not belong to this bucket");
        }

        wish.setTitle(wishRequest.getTitle());
        wish.setDescription(wishRequest.getDescription());

        Wish updatedWish = wishService.updateWish(wish);
        return ResponseEntity.ok(new WishResponse(updatedWish));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> markWishAsCompleted(@PathVariable Long bucketId, @PathVariable Long id) {
        Optional<Wish> wishOptional = wishService.getWishById(id);
        if (wishOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Wish wish = wishOptional.get();
        // 检查是否属于指定的Bucket
        if (!wish.getBucket().getId().equals(bucketId)) {
            return ResponseEntity.badRequest().body("Wish does not belong to this bucket");
        }

        wish.markAsCompleted();
        Wish updatedWish = wishService.updateWish(wish);
        return ResponseEntity.ok(new WishResponse(updatedWish));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWish(@PathVariable Long bucketId, @PathVariable Long id) {
        Optional<Wish> wishOptional = wishService.getWishById(id);
        if (wishOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Wish wish = wishOptional.get();
        // 检查是否属于指定的Bucket
        if (!wish.getBucket().getId().equals(bucketId)) {
            return ResponseEntity.badRequest().body("Wish does not belong to this bucket");
        }

        wishService.deleteWish(id);
        return ResponseEntity.ok().build();
    }
}
