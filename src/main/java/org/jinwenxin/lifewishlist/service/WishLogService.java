package org.jinwenxin.lifewishlist.service;

import org.jinwenxin.lifewishlist.model.Wish;
import org.jinwenxin.lifewishlist.model.WishLog;

import java.util.List;
import java.util.Optional;

public interface WishLogService {
    WishLog createWishLog(WishLog wishLog);
    WishLog updateWishLog(WishLog wishLog);
    void deleteWishLog(Long wishLogId);
    Optional<WishLog> getWishLogById(Long wishLogId);
    List<WishLog> getWishLogsByWish(Wish wish);
    boolean isWishLogOwnedByUser(Long wishLogId, Long userId);
}