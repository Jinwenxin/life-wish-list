package org.jinwenxin.lifewishlist.controller;

import org.jinwenxin.lifewishlist.dto.request.WishLogRequest;
import org.jinwenxin.lifewishlist.dto.response.WishLogResponse;
import org.jinwenxin.lifewishlist.model.Wish;
import org.jinwenxin.lifewishlist.model.WishLog;
import org.jinwenxin.lifewishlist.service.WishLogService;
import org.jinwenxin.lifewishlist.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishes/{wishId}/logs")
public class WishLogController {

    @Autowired
    private WishLogService wishLogService;

    @Autowired
    private WishService wishService;

    @PostMapping
    public ResponseEntity<?> createWishLog(@PathVariable Long wishId, @Valid @RequestBody WishLogRequest wishLogRequest) {
        Optional<Wish> wishOptional = wishService.getWishById(wishId);
        if (wishOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WishLog wishLog = new WishLog();
        wishLog.setContent(wishLogRequest.getContent());
        wishLog.setImageUrls(wishLogRequest.getImageUrls());
        wishLog.setWish(wishOptional.get());
        wishLog.setLoggedAt(wishLogRequest.getLoggedAt());

        WishLog savedWishLog = wishLogService.createWishLog(wishLog);
        return ResponseEntity.ok(new WishLogResponse(savedWishLog));
    }

    @GetMapping
    public ResponseEntity<List<WishLogResponse>> getWishLogsByWish(@PathVariable Long wishId) {
        Optional<Wish> wishOptional = wishService.getWishById(wishId);
        if (wishOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<WishLog> wishLogs = wishLogService.getWishLogsByWish(wishOptional.get());
        List<WishLogResponse> wishLogResponses = wishLogs.stream()
                .map(WishLogResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(wishLogResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWishLogById(@PathVariable Long wishId, @PathVariable Long id) {
        Optional<WishLog> wishLogOptional = wishLogService.getWishLogById(id);
        if (wishLogOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WishLog wishLog = wishLogOptional.get();
        // 检查是否属于指定的Wish
        if (!wishLog.getWish().getId().equals(wishId)) {
            return ResponseEntity.badRequest().body("Wish log does not belong to this wish");
        }

        return ResponseEntity.ok(new WishLogResponse(wishLog));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWishLog(@PathVariable Long wishId, @PathVariable Long id, @Valid @RequestBody WishLogRequest wishLogRequest) {
        Optional<WishLog> wishLogOptional = wishLogService.getWishLogById(id);
        if (wishLogOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WishLog wishLog = wishLogOptional.get();
        // 检查是否属于指定的Wish
        if (!wishLog.getWish().getId().equals(wishId)) {
            return ResponseEntity.badRequest().body("Wish log does not belong to this wish");
        }

        wishLog.setContent(wishLogRequest.getContent());
        wishLog.setImageUrls(wishLogRequest.getImageUrls());
        wishLog.setLoggedAt(wishLogRequest.getLoggedAt());

        WishLog updatedWishLog = wishLogService.updateWishLog(wishLog);
        return ResponseEntity.ok(new WishLogResponse(updatedWishLog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWishLog(@PathVariable Long wishId, @PathVariable Long id) {
        Optional<WishLog> wishLogOptional = wishLogService.getWishLogById(id);
        if (wishLogOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WishLog wishLog = wishLogOptional.get();
        // 检查是否属于指定的Wish
        if (!wishLog.getWish().getId().equals(wishId)) {
            return ResponseEntity.badRequest().body("Wish log does not belong to this wish");
        }

        wishLogService.deleteWishLog(id);
        return ResponseEntity.ok().build();
    }
}
