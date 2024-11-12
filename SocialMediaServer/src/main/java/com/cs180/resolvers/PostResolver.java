package com.cs180.resolvers;

import java.util.UUID;

import com.cs180.api.InternalServerError;
import com.cs180.api.Request;
import com.cs180.resolvers.ResolverTools.AuthGuard;
import com.cs180.resolvers.ResolverTools.BaseResolver;
import com.cs180.resolvers.ResolverTools.Endpoint;
import com.cs180.resolvers.ResolverTools.Resolver;
import com.cs180.services.PostService;

import com.cs180.dtos.CreatePostDTO;
import com.cs180.dtos.DeletePostDTO;
import com.cs180.dtos.UnlikePostDTO;
import com.cs180.dtos.LikePostDTO;

@Resolver(basePath = "/post")
public class PostResolver extends BaseResolver {
    public PostResolver() {
        new ResolverTools().super();
    }

    @AuthGuard()
    @Endpoint(endpoint = "/create", method = Request.EMethod.POST, requestBodyType = CreatePostDTO.class)
    public void createPost(Request<CreatePostDTO> request) {
        UUID userId = request.getUserId();
        String messagePost = request.getBody().getMessagePost();
        String date = request.getBody().getDate();
        int likes = 0;
        String imageURL = request.getBody().getImageURL();

        if (!PostService.createPost(userId, messagePost, date, likes, imageURL)) {
            throw new InternalServerError("Failed to Create Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/delete", method = Request.EMethod.POST, requestBodyType = DeletePostDTO.class)
    public void deletePost(Request<DeletePostDTO> request) {
        UUID postId = request.getBody().getPostId();

        if (!PostService.deletePost(postId)) {
            throw new InternalServerError("Failed to Delete Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/like", method = Request.EMethod.POST, requestBodyType = LikePostDTO.class)
    public void likePost(Request<LikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = UUID.randomUUID();

        if (!PostService.likePost(userId, postId)) {
            throw new InternalServerError("Failed to Like Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/unlike", method = Request.EMethod.POST, requestBodyType = UnlikePostDTO.class)
    public void unlikePost(Request<UnlikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = UUID.randomUUID();

        if (!PostService.unlikePost(userId, postId)) {
            throw new InternalServerError("Failed to Unlike Post");
        }
    }
}
