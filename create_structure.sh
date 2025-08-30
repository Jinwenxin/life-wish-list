#!/bin/bash

# 创建目录结构
mkdir -p src/main/java/com/jinwenxin/lifewishlist/{config,controller,model,repository,service,dto/{request,response},exception}
mkdir -p src/main/resources/{static,templates}
mkdir -p src/test/java/com/jinwenxin/lifewishlist

# 创建配置类文件
touch src/main/java/com/jinwenxin/lifewishlist/config/{SecurityConfig.java,WebConfig.java,OpenApiConfig.java}

# 创建控制器类文件
touch src/main/java/com/jinwenxin/lifewishlist/controller/{AuthController.java,BucketController.java,WishController.java,WishLogController.java}

# 创建模型类文件
touch src/main/java/com/jinwenxin/lifewishlist/model/{User.java,Bucket.java,Wish.java,WishLog.java}

# 创建仓库类文件
touch src/main/java/com/jinwenxin/lifewishlist/repository/{UserRepository.java,BucketRepository.java,WishRepository.java,WishLogRepository.java}

# 创建服务类文件
touch src/main/java/com/jinwenxin/lifewishlist/service/{UserService.java,BucketService.java,WishService.java,WishLogService.java}

# 创建DTO类文件
touch src/main/java/com/jinwenxin/lifewishlist/dto/request/{LoginRequest.java,RegisterRequest.java,BucketRequest.java,WishRequest.java,WishLogRequest.java}
touch src/main/java/com/jinwenxin/lifewishlist/dto/response/{AuthResponse.java,BucketResponse.java,WishResponse.java,WishLogResponse.java}

# 创建异常类文件
touch src/main/java/com/jinwenxin/lifewishlist/exception/{GlobalExceptionHandler.java,ResourceNotFoundException.java,AuthenticationException.java}

# 创建资源文件
touch src/main/resources/application.properties

echo "目录结构创建完成！"
