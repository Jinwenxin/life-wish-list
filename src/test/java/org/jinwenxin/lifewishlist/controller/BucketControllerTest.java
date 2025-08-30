package org.jinwenxin.lifewishlist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jinwenxin.lifewishlist.dto.request.BucketRequest;
import org.jinwenxin.lifewishlist.model.Bucket;
import org.jinwenxin.lifewishlist.model.User;
import org.jinwenxin.lifewishlist.service.BucketService;
import org.jinwenxin.lifewishlist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = BucketController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {UserDetailsService.class, org.jinwenxin.lifewishlist.util.JwtAuthenticationFilter.class}))
@WithMockUser(username = "testuser") // 模拟已认证用户
class BucketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BucketService bucketService;

    @MockBean
    private UserService userService;

    private User testUser;
    private Bucket testBucket;
    private BucketRequest bucketRequest;

    @BeforeEach
    void setUp() {
        // 创建测试用户
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");

        // 创建测试Bucket
        testBucket = new Bucket();
        testBucket.setId(1L);
        testBucket.setTitle("Test Bucket");
        testBucket.setDescription("Test Description");
        testBucket.setIsPublic(false);
        testBucket.setUser(testUser);
        testBucket.setCreatedAt(LocalDateTime.now());
        testBucket.setUpdatedAt(LocalDateTime.now());

        // 创建Bucket请求对象
        bucketRequest = new BucketRequest();
        bucketRequest.setTitle("Test Bucket");
        bucketRequest.setDescription("Test Description");
        bucketRequest.setIsPublic(false);
    }

    @Test
    void createBucket_Success() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.createBucket(any(Bucket.class))).thenReturn(testBucket);

        // When & Then
        mockMvc.perform(post("/api/buckets")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bucketRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Bucket"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).createBucket(any(Bucket.class));
    }

    @Test
    void createBucket_UserNotFound_ReturnsBadRequest() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/api/buckets")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bucketRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, never()).createBucket(any(Bucket.class));
    }

    @Test
    void getBucketsByUser_Success() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.getBucketsByUser(testUser)).thenReturn(List.of(testBucket));

        // When & Then
        mockMvc.perform(get("/api/buckets").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Bucket"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).getBucketsByUser(testUser);
    }

    @Test
    void getBucketsByUser_UserNotFound_ReturnsNotFound() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/buckets").with(csrf()))
                .andExpect(status().isNotFound());

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, never()).getBucketsByUser(any(User.class));
    }

    @Test
    void getBucketById_Success() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.getBucketById(1L)).thenReturn(Optional.of(testBucket));

        // When & Then
        mockMvc.perform(get("/api/buckets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Bucket"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).getBucketById(1L);
    }

    @Test
    void getBucketById_UserNotFound_ReturnsBadRequest() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/buckets/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, never()).getBucketById(anyLong());
    }

    @Test
    void getBucketById_BucketNotFound_ReturnsNotFound() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.getBucketById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/buckets/1"))
                .andExpect(status().isNotFound());

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).getBucketById(1L);
    }

    @Test
    void updateBucket_Success() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.getBucketById(1L)).thenReturn(Optional.of(testBucket));
        when(bucketService.updateBucket(any(Bucket.class))).thenReturn(testBucket);

        // When & Then
        mockMvc.perform(put("/api/buckets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bucketRequest)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Bucket"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).getBucketById(1L);
        verify(bucketService, times(1)).updateBucket(any(Bucket.class));
    }

    @Test
    void updateBucket_UserNotFound_ReturnsBadRequest() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/api/buckets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bucketRequest)).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, never()).getBucketById(anyLong());
    }

    @Test
    void updateBucket_BucketNotFound_ReturnsNotFound() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.getBucketById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/api/buckets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bucketRequest)).with(csrf()))
                .andExpect(status().isNotFound());

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).getBucketById(1L);
        verify(bucketService, never()).updateBucket(any(Bucket.class));
    }

    @Test
    void deleteBucket_Success() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.getBucketById(1L)).thenReturn(Optional.of(testBucket));

        // When & Then
        mockMvc.perform(delete("/api/buckets/1").with(csrf()))
                .andExpect(status().isOk());

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).getBucketById(1L);
        verify(bucketService, times(1)).deleteBucket(1L);
    }

    @Test
    void deleteBucket_UserNotFound_ReturnsBadRequest() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/api/buckets/1").with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, never()).getBucketById(anyLong());
        verify(bucketService, never()).deleteBucket(anyLong());
    }

    @Test
    void deleteBucket_BucketNotFound_ReturnsNotFound() throws Exception {
        // Given
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(bucketService.getBucketById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/api/buckets/1").with(csrf()))
                .andExpect(status().isNotFound());

        // Verify
        verify(userService, times(1)).getUserByUsername("testuser");
        verify(bucketService, times(1)).getBucketById(1L);
        verify(bucketService, never()).deleteBucket(anyLong());
    }

    private String asJsonString(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
