package org.jinwenxin.lifewishlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 资源未找到异常
 *
 * 当请求的资源（如用户、愿望清单、愿望项等）不存在时抛出此异常。
 * 此异常会自动映射为 HTTP 404 (Not Found) 状态码。
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    /**
     * 构造函数
     *
     * @param resourceName 资源名称（如 "User", "Bucket", "Wish"）
     * @param fieldName 查找字段名称（如 "id", "username", "email"）
     * @param fieldValue 查找字段值
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * 获取资源名称
     *
     * @return 资源名称
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * 获取查找字段名称
     *
     * @return 查找字段名称
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 获取查找字段值
     *
     * @return 查找字段值
     */
    public Object getFieldValue() {
        return fieldValue;
    }
}