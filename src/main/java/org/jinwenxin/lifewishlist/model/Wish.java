package org.jinwenxin.lifewishlist.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "TINYINT")
    private WishStatus status = WishStatus.NOT_STARTED;

    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bucket_id", nullable = false)
    private Bucket bucket;

    @OneToMany(mappedBy = "wish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishLog> logs = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 枚举定义愿望状态
    public enum WishStatus {
        NOT_STARTED(0),
        IN_PROGRESS(1),
        COMPLETED(2);

        private final int value;

        WishStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static WishStatus fromValue(int value) {
            for (WishStatus status : WishStatus.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid WishStatus value: " + value);
        }
    }

    // 构造函数（不含id和自动生成字段）
    public Wish(String title, String description, WishStatus status, Bucket bucket) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.bucket = bucket;
    }

    // 标记完成的方法
    public void markAsCompleted() {
        this.status = WishStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
}