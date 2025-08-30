package org.jinwenxin.lifewishlist.controller;

import org.jinwenxin.lifewishlist.dto.request.BucketRequest;
import org.jinwenxin.lifewishlist.dto.response.BucketResponse;
import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.User;
import org.jinwenxin.lifewishlist.service.BucketService;
import org.jinwenxin.lifewishlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buckets")
public class BucketController {

    @Autowired
    private BucketService bucketService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createBucket(@Valid @RequestBody BucketRequest bucketRequest, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Bucket bucket = new Bucket();
        bucket.setTitle(bucketRequest.getTitle());
        bucket.setDescription(bucketRequest.getDescription());
        bucket.setIsPublic(bucketRequest.getIsPublic());
        bucket.setUser(userOptional.get());

        Bucket savedBucket = bucketService.createBucket(bucket);
        return ResponseEntity.ok(new BucketResponse(savedBucket));
    }

    @GetMapping
    public ResponseEntity<List<BucketResponse>> getBucketsByUser(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Bucket> buckets = bucketService.getBucketsByUser(userOptional.get());
        List<BucketResponse> bucketResponses = buckets.stream()
                .map(BucketResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bucketResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBucketById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Optional<Bucket> bucketOptional = bucketService.getBucketById(id);
        if (bucketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Bucket bucket = bucketOptional.get();
        // 检查权限：要么是公开的，要么是用户自己的
        if (!bucket.getIsPublic() && !bucket.getUser().getId().equals(userOptional.get().getId())) {
            return ResponseEntity.status(403).body("Access denied");
        }

        return ResponseEntity.ok(new BucketResponse(bucket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBucket(@PathVariable Long id, @Valid @RequestBody BucketRequest bucketRequest, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Optional<Bucket> bucketOptional = bucketService.getBucketById(id);
        if (bucketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Bucket bucket = bucketOptional.get();
        // 检查权限：只能修改自己的Bucket
        if (!bucket.getUser().getId().equals(userOptional.get().getId())) {
            return ResponseEntity.status(403).body("Access denied");
        }

        bucket.setTitle(bucketRequest.getTitle());
        bucket.setDescription(bucketRequest.getDescription());
        bucket.setIsPublic(bucketRequest.getIsPublic());

        Bucket updatedBucket = bucketService.updateBucket(bucket);
        return ResponseEntity.ok(new BucketResponse(updatedBucket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBucket(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Optional<Bucket> bucketOptional = bucketService.getBucketById(id);
        if (bucketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Bucket bucket = bucketOptional.get();
        // 检查权限：只能删除自己的Bucket
        if (!bucket.getUser().getId().equals(userOptional.get().getId())) {
            return ResponseEntity.status(403).body("Access denied");
        }

        bucketService.deleteBucket(id);
        return ResponseEntity.ok().build();
    }
}
