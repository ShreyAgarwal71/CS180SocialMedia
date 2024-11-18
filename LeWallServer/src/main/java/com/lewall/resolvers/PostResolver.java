package com.lewall.resolvers;

import java.util.UUID;

import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.resolvers.ResolverTools.AuthGuard;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;
import com.lewall.services.PostService;
import com.lewall.dtos.CreatePostDTO;
import com.lewall.dtos.DeletePostDTO;
import com.lewall.dtos.UnlikePostDTO;
import com.lewall.interfaces.IPostResolver;
import com.lewall.dtos.LikePostDTO;
import com.lewall.dtos.CommentsDTO;
import com.lewall.dtos.PostCommentsDTO;
import com.lewall.dtos.HidePostDTO;
import com.lewall.dtos.PublicPrivateDTO;

/**
 * A class to resolve Post-related requests
 *
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
@Resolver(basePath = "/post")
public class PostResolver implements BaseResolver, IPostResolver {
    @AuthGuard()
    @Endpoint(endpoint = "/create", method = Request.EMethod.POST, requestBodyType = CreatePostDTO.class)
    public void createPost(Request<CreatePostDTO> request) {
        UUID userId = request.getUserId();
        String messagePost = request.getBody().getMessagePost();
        String date = request.getBody().getDate();
        int likes = 0;
        String imageURL = request.getBody().getImageURL();
        UUID classId = request.getBody().getClassId();

        if (!PostService.createPost(userId, messagePost, date, likes, imageURL, classId)) {
            throw new InternalServerError("Failed to Create Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/delete", method = Request.EMethod.POST, requestBodyType = DeletePostDTO.class)
    public void deletePost(Request<DeletePostDTO> request) {
        UUID userId = request.getUserId();
        UUID postId = request.getBody().getPostId();

        if (!PostService.deletePost(userId, postId)) {
            throw new InternalServerError("Failed to Delete Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/like", method = Request.EMethod.POST, requestBodyType = LikePostDTO.class)
    public void likePost(Request<LikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        if (!PostService.likePost(userId, postId)) {
            throw new InternalServerError("Failed to Like Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/unlike", method = Request.EMethod.POST, requestBodyType = UnlikePostDTO.class)
    public void unlikePost(Request<UnlikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        if (!PostService.unlikePost(userId, postId)) {
            throw new InternalServerError("Failed to Unlike Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/hide", method = Request.EMethod.POST, requestBodyType = HidePostDTO.class)
    public void hidePost(Request<HidePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        if (!PostService.hidePost(userId, postId)) {
            throw new InternalServerError("Failed to hide Post");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/getComments", method = Request.EMethod.GET, requestBodyType = PostCommentsDTO.class, responseBodyType = CommentsDTO.class)
    public CommentsDTO getPosts(Request<PostCommentsDTO> request) {
        UUID postId = request.getBody().getPostId();

        CommentsDTO comments = new CommentsDTO(PostService.getComments(postId));
        if (PostService.getComments(postId) == null) {
            throw new InternalServerError("Failed to get Comments");
        }

        return comments;
    }

    @AuthGuard()
    @Endpoint(endpoint = "/makePrivate", method = Request.EMethod.POST, requestBodyType = PublicPrivateDTO.class)
    public void makePostPrivate(Request<PublicPrivateDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();
        boolean isPrivate = request.getBody().getIsPrivate();

        if (!PostService.makePostPrivate(userId, postId, isPrivate)) {
            throw new InternalServerError("Failed to make Post Private or Public");
        }
    }
}
