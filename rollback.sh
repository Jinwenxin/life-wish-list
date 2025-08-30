#!/bin/bash

# 安全删除方式 - 只删除空目录和通过脚本创建的文件

# 删除配置类文件（如果为空）
find src/main/java -name "SecurityConfig.java" -type f -empty -delete
find src/main/java -name "WebConfig.java" -type f -empty -delete
find src/main/java -name "OpenApiConfig.java" -type f -empty -delete

# 删除控制器类文件（如果为空）
find src/main/java -name "AuthController.java" -type f -empty -delete
find src/main/java -name "BucketController.java" -type f -empty -delete
find src/main/java -name "WishController.java" -type f -empty -delete
find src/main/java -name "WishLogController.java" -type f -empty -delete

# 删除模型类文件（如果为空）
find src/main/java -name "User.java" -type f -empty -delete
find src/main/java -name "Bucket.java" -type f -empty -delete
find src/main/java -name "Wish.java" -type f -empty -delete
find src/main/java -name "WishLog.java" -type f -empty -delete

# 删除仓库类文件（如果为空）
find src/main/java -name "UserRepository.java" -type f -empty -delete
find src/main/java -name "BucketRepository.java" -type f -empty -delete
find src/main/java -name "WishRepository.java" -type f -empty -delete
find src/main/java -name "WishLogRepository.java" -type f -empty -delete

# 删除服务类文件（如果为空）
find src/main/java -name "UserService.java" -type f -empty -delete
find src/main/java -name "BucketService.java" -type f -empty -delete
find src/main/java -name "WishService.java" -type f -empty -delete
find src/main/java -name "WishLogService.java" -type f -empty -delete

# 删除DTO类文件（如果为空）
find src/main/java -name "LoginRequest.java" -type f -empty -delete
find src/main/java -name "RegisterRequest.java" -type f -empty -delete
find src/main/java -name "BucketRequest.java" -type f -empty -delete
find src/main/java -name "WishRequest.java" -type f -empty -delete
find src/main/java -name "WishLogRequest.java" -type f -empty -delete
find src/main/java -name "AuthResponse.java" -type f -empty -delete
find src/main/java -name "BucketResponse.java" -type f -empty -delete
find src/main/java -name "WishResponse.java" -type f -empty -delete
find src/main/java -name "WishLogResponse.java" -type f -empty -delete

# 删除异常类文件（如果为空）
find src/main/java -name "GlobalExceptionHandler.java" -type f -empty -delete
find src/main/java -name "ResourceNotFoundException.java" -type f -empty -delete
find src/main/java -name "AuthenticationException.java" -type f -empty -delete

# 删除空目录
find src/main/java -type d -empty -delete
find src/test/java -type d -empty -delete

echo "安全删除完成！"
