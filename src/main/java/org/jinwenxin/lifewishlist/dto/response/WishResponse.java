package org.jinwenxin.lifewishlist.dto.response;

import org.jinwenxin.lifewishlist.model.Wish;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonFormat;
public class WishResponse {
    private Long id;
    private String title;
    private String description;
    private Wish.WishStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime completedAt;
    private Long bucketId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;
    private List<WishLogResponse> logs;

    public WishResponse() {
    }

    public WishResponse(Wish wish) {
        this.id = wish.getId();
        this.title = wish.getTitle();
        this.description = wish.getDescription();
        this.status = wish.getStatus();
        this.completedAt = wish.getCompletedAt();
        this.bucketId = wish.getBucket().getId();
        this.createdAt = wish.getCreatedAt();
        this.updatedAt = wish.getUpdatedAt();

        if (wish.getLogs() != null) {
            this.logs = wish.getLogs().stream()
                    .map(WishLogResponse::new)
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

    public Wish.WishStatus getStatus() {
        return status;
    }

    public void setStatus(Wish.WishStatus status) {
        this.status = status;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getBucketId() {
        return bucketId;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
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

    public List<WishLogResponse> getLogs() {
        return logs;
    }

    public void setLogs(List<WishLogResponse> logs) {
        this.logs = logs;
    }
}