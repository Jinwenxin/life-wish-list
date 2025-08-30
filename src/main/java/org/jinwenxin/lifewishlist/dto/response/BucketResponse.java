package org.jinwenxin.lifewishlist.dto.response;

import org.jinwenxin.lifewishlist.model.Bucket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BucketResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean isPublic;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<WishResponse> wishes;

    public BucketResponse() {
    }

    public BucketResponse(Bucket bucket) {
        this.id = bucket.getId();
        this.title = bucket.getTitle();
        this.description = bucket.getDescription();
        this.isPublic = bucket.getIsPublic();
        this.userId = bucket.getUser().getId();
        this.createdAt = bucket.getCreatedAt();
        this.updatedAt = bucket.getUpdatedAt();

        if (bucket.getWishes() != null) {
            this.wishes = bucket.getWishes().stream()
                    .map(WishResponse::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<WishResponse> getWishes() {
        return wishes;
    }

    public void setWishes(List<WishResponse> wishes) {
        this.wishes = wishes;
    }
}