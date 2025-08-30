package org.jinwenxin.lifewishlist.dto.response;

import org.jinwenxin.lifewishlist.model.WishLog;

import java.time.LocalDateTime;
import java.util.List;

public class WishLogResponse {
    private Long id;
    private String content;
    private List<String> imageUrls;
    private Long wishId;
    private LocalDateTime loggedAt;
    private LocalDateTime createdAt;

    public WishLogResponse() {
    }

    public WishLogResponse(WishLog wishLog) {
        this.id = wishLog.getId();
        this.content = wishLog.getContent();
        this.imageUrls = wishLog.getImageUrls();
        this.wishId = wishLog.getWish().getId();
        this.loggedAt = wishLog.getLoggedAt();
        this.createdAt = wishLog.getCreatedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Long getWishId() {
        return wishId;
    }

    public void setWishId(Long wishId) {
        this.wishId = wishId;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
