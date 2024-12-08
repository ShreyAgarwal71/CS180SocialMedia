package com.lewall.resolvers;

import java.util.UUID;

import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.db.models.Post;
import com.lewall.resolvers.ResolverTools.AuthGuard;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;
import com.lewall.services.PostService;
import com.lewall.services.UserService;
import com.lewall.dtos.CreatePostDTO;
import com.lewall.dtos.DeletePostDTO;
import com.lewall.dtos.UnlikePostDTO;
import com.lewall.dtos.UserIdDTO;
import com.lewall.interfaces.IPostResolver;
import com.lewall.dtos.LikePostDTO;
import com.lewall.dtos.CommentsDTO;
import com.lewall.dtos.PostCommentsDTO;
import com.lewall.dtos.PostsDTO;
import com.lewall.dtos.HidePostDTO;
import com.lewall.dtos.PostDTO;
import com.lewall.dtos.PublicPrivateDTO;
import com.lewall.dtos.ClassesDTO;

/**
 * A class to resolve Post-related requests
 *
 * @author Shrey Agarwal, Ates Isfendiyaroglu
 * @version 8 December 2024
 */
@Resolver(basePath = "/post")
public class PostResolver implements BaseResolver, IPostResolver {
    /**
     * Get posts of a user
     * 
     * @return {@link PostsDTO}
     */

    @AuthGuard()
    @Endpoint(endpoint = "/all", method = Request.EMethod.POST, requestBodyType = UserIdDTO.class, responseBodyType = PostsDTO.class)
    public PostsDTO getPosts(Request<UserIdDTO> request) {
        UUID userId = request.getBody().getUserId();
        return new PostsDTO(UserService.getPosts(userId));
    }

    @AuthGuard()
    @Endpoint(endpoint = "/create", method = Request.EMethod.POST, requestBodyType = CreatePostDTO.class)
    public void createPost(Request<CreatePostDTO> request) {
        UUID userId = request.getUserId();
        String messagePost = request.getBody().getMessagePost();
        String date = request.getBody().getDate();
        int likes = 0;
        int dislikes = 0;
        String imageURL = request.getBody().getImageURL();
        String classId = request.getBody().getClassId();

        if (!PostService.createPost(userId, messagePost, date, likes, dislikes, imageURL, classId)) {
            throw new InternalServerError("Failed to Create Post");
        }
    }

    /**
     * Delete a post
     * 
     * @param request
     *            {@link Request} with {@link DeletePostDTO} body
     * @throws InternalServerError
     *             if unable to delete post
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/delete", method = Request.EMethod.POST, requestBodyType = DeletePostDTO.class)
    public void deletePost(Request<DeletePostDTO> request) {
        UUID userId = request.getUserId();
        UUID postId = request.getBody().getPostId();

        if (!PostService.deletePost(userId, postId)) {
            throw new InternalServerError("Failed to Delete Post");
        }
    }

    /**
     * Like a post
     * 
     * @param request
     *            {@link Request} with {@link LikePostDTO} body
     * @throws InternalServerError
     *             if unable to like post
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/like", method = Request.EMethod.POST, requestBodyType = LikePostDTO.class, responseBodyType = PostDTO.class)
    public PostDTO likePost(Request<LikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        Post updatedPost = PostService.likePost(userId, postId);
        if (updatedPost == null) {
            throw new InternalServerError("Failed to Like Post");
        }

        return new PostDTO(updatedPost);
    }

    /**
     * Dislike a post
     * 
     * @param request
     *            {@link Request} with {@link LikePostDTO} body
     * @throws InternalServerError
     *             if unable to like post
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/dislike", method = Request.EMethod.POST, requestBodyType = LikePostDTO.class, responseBodyType = PostDTO.class)
    public PostDTO dislikePost(Request<LikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        Post updatedPost = PostService.dislikePost(userId, postId);
        if (updatedPost == null) {
            throw new InternalServerError("Failed to Dislike Post");
        }

        return new PostDTO(updatedPost);
    }

    /**
     * Unlike a post
     * 
     * @param request
     *            {@link Request} with {@link UnlikePostDTO} body
     * @throws InternalServerError
     *             if unable to unlike post
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/unlike", method = Request.EMethod.POST, requestBodyType = UnlikePostDTO.class, responseBodyType = PostDTO.class)
    public PostDTO unlikePost(Request<UnlikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        Post updatedPost = PostService.unlikePost(userId, postId);
        if (updatedPost == null) {
            throw new InternalServerError("Failed to Unlike Post");
        }

        return new PostDTO(updatedPost);
    }

    /**
     * Un-dislike a post
     * 
     * @param request
     *            {@link Request} with {@link UnlikePostDTO} body
     * @throws InternalServerError
     *             if unable to unlike post
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/unDislike", method = Request.EMethod.POST, requestBodyType = UnlikePostDTO.class, responseBodyType = PostDTO.class)
    public PostDTO unDislikePost(Request<UnlikePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        Post updatedPost = PostService.unDislikePost(userId, postId);
        if (updatedPost == null) {
            throw new InternalServerError("Failed to Unlike Post");
        }

        return new PostDTO(updatedPost);
    }

    /**
     * Hide a post
     * 
     * @param request
     *            {@link Request} with {@link HidePostDTO} body
     * @throws InternalServerError
     *             if unable to hide post
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/hide", method = Request.EMethod.POST, requestBodyType = HidePostDTO.class)
    public void hidePost(Request<HidePostDTO> request) {
        UUID postId = request.getBody().getPostId();
        UUID userId = request.getUserId();

        if (!PostService.hidePost(userId, postId)) {
            throw new InternalServerError("Failed to hide Post");
        }
    }

    /**
     * Get comments for a post
     * 
     * @param request
     *            {@link Request} with {@link PostCommentsDTO} body
     * @throws InternalServerError
     *             if unable to get comments
     * @return {@link CommentsDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getComments", method = Request.EMethod.POST, requestBodyType = PostCommentsDTO.class, responseBodyType = CommentsDTO.class)
    public CommentsDTO getComments(Request<PostCommentsDTO> request) {
        UUID postId = request.getBody().getPostId();

        CommentsDTO comments = new CommentsDTO(PostService.getComments(postId));
        if (PostService.getComments(postId) == null) {
            throw new InternalServerError("Failed to get Comments");
        }

        return comments;
    }

    /**
     * Make a post private or public
     * 
     * @param request
     *            {@link Request} with {@link PublicPrivateDTO} body
     * @throws InternalServerError
     *             if unable to make post private or public
     * @return void
     */
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

    /**
     * Get classes
     * 
     * @return {@link ClassesDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getClasses", method = Request.EMethod.GET, responseBodyType = ClassesDTO.class)
    public ClassesDTO getClasses(Request<Void> request) {
        return new ClassesDTO();
    }
}
