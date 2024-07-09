package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.model.Community;
import com.m0p4rk.pet911.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    // View rendering methods

    @GetMapping
    public String communityMain(Model model) {
        // 여기에서 필요한 데이터를 모델에 추가할 수 있습니다.
        return "community";
    }

    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Community post = communityService.getPost(id);
        if (post == null) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }
        communityService.incrementViewCount(id);
        model.addAttribute("post", post);
        return "community-view";
    }

    @GetMapping("/write")
    public String writePostForm(Model model) {
        model.addAttribute("post", new Community());
        return "community-write";
    }

    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Community post = communityService.getPost(id);
        if (post != null) {
            model.addAttribute("post", post);
            return "community-edit";
        } else {
            return "error/404";
        }
    }

    // RESTful API methods

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody Community community) {
        Community createdPost = communityService.createPost(community);
        Map<String, Object> response = new HashMap<>();
        response.put("post", createdPost);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPostDetail(@PathVariable Long id) {
        Community post = communityService.getPost(id);
        if (post != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("post", post);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Community> posts = communityService.getAllPosts(page, size);
        int totalPosts = communityService.getTotalPostCount();
        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("currentPage", page);
        response.put("totalItems", totalPosts);
        response.put("totalPages", (int) Math.ceil((double) totalPosts / size));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/category/{category}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPostsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Community> posts = communityService.getPostsByCategory(category, page, size);
        int totalPosts = communityService.getPostCountByCategory(category);
        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("currentPage", page);
        response.put("totalItems", totalPosts);
        response.put("totalPages", (int) Math.ceil((double) totalPosts / size));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id, @RequestBody Community community) {
        Community updatedPost = communityService.updatePost(id, community);
        Map<String, Object> response = new HashMap<>();
        response.put("post", updatedPost);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        communityService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/{id}/view")
    @ResponseBody
    public ResponseEntity<Void> incrementViewCount(@PathVariable Long id) {
        communityService.incrementViewCount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/{id}/pin")
    @ResponseBody
    public ResponseEntity<Void> togglePin(@PathVariable Long id) {
        communityService.togglePin(id);
        return ResponseEntity.ok().build();
    }
}