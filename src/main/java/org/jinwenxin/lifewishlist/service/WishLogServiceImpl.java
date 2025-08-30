package org.jinwenxin.lifewishlist.service;

import org.jinwenxin.lifewishlist.model.Wish;
import org.jinwenxin.lifewishlist.model.WishLog;
import org.jinwenxin.lifewishlist.repository.WishLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishLogServiceImpl implements WishLogService {

    @Autowired
    private WishLogRepository wishLogRepository;

    @Autowired
    private WishService wishService;

    @Override
    public WishLog createWishLog(WishLog wishLog) {
        return wishLogRepository.save(wishLog);
    }

    @Override
    public WishLog updateWishLog(WishLog wishLog) {
        return wishLogRepository.save(wishLog);
    }

    @Override
    public void deleteWishLog(Long wishLogId) {
        wishLogRepository.deleteById(wishLogId);
    }

    @Override
    public Optional<WishLog> getWishLogById(Long wishLogId) {
        return wishLogRepository.findById(wishLogId);
    }

    @Override
    public List<WishLog> getWishLogsByWish(Wish wish) {
        return wishLogRepository.findByWish(wish);
    }

    @Override
    public boolean isWishLogOwnedByUser(Long wishLogId, Long userId) {
        Optional<WishLog> wishLog = wishLogRepository.findById(wishLogId);
        if (wishLog.isPresent()) {
            Wish wish = wishLog.get().getWish();
            return wishService.isWishOwnedByUser(wish.getId(), userId);
        }
        return false;
    }
}