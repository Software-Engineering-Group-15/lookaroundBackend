package lookaroundBackend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lookaroundBackend.entity.Comment;
import lookaroundBackend.entity.Post;
import lookaroundBackend.service.PublishService;
import lookaroundBackend.service.SearchService;

@RestController
public class PostController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private PublishService publishService;

    //生成response
    private Map<String,Object> getResonse(Integer code, Map<String,Object> data){
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("code", code);
        response.put("data", data);
        return response;
    }
    
    // 按ID获取Post
    @RequestMapping(value = "/post/getmessage/{post_id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPostById(@PathVariable(name = "post_id", required = true) Integer post_id) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        try{
            Post post = searchService.findPost(post_id);
            data.put("post", post);
            response = getResonse(200, data);
            return response;
        }catch(NoSuchElementException e){
            data.clear();
            data.put("msg", "post not found");
            response = getResonse(300, data);
            return response;
        }catch(Exception e){
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }

    }

    // 上传Post
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> newPost(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        try{
            Map<String,Object> newpost = (Map<String,Object>)newRequest.get("post");
            Map<String,Object> locationMap = (Map<String,Object>)newRequest.get("location");
            Map<String,Object> publisherMap = (Map<String,Object>)newRequest.get("publisher");

             //need to discuss
            String location = locationMap.get("long").toString()+" "+locationMap.get("lat").toString();
            //Post post = postService.createPost(publisherMap.get("userName"), newpost.get("text"), location, null);
            
            publishService.publishPost(publisherMap.get("userName"), newpost.get("text"), location, null);
            //end

            data.put("msg", "success");
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }
    }

    // 请求动态评论
    @RequestMapping(value = {"/post/comments/{post_id}/{limit}/{start}","/post/comments/{post_id}",
        "/posts/comments/{post_id}/{limit}"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getComment(@PathVariable(name = "post_id", required = true) Integer post_id,
                                        @PathVariable(name = "limit") Integer limit,
                                        @PathVariable(name = "start") Integer start){
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        try{
            if(limit == null) limit = 10;
            if(start == null) start = 1;
            
            //need to discuss
            Post post = searchService.findPost(post_id);

            // TODO: JPA要求用SET实现CommentList。所以limit的要求有些尴尬

            commentList.addAll(post.getCommentList());
            data.put("commentList", commentList);
            //end

            response = getResonse(200, data);
            return response;
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
            return response;
        }
    }

    // 点赞
    @RequestMapping(value = "/post/favor", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> newFavor(@RequestBody Integer id){
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        return getResonse(404, data);
        try{

            // TODO: 实现点赞功能需要从数据层开始，报告之前暂时放置吧
            //need to discuss
            //commentSerive.favor(id);
            //end

            data.put("msg", "success");
            response = getResonse(200, data);
            return response;
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
            return response;
        }
    }

    // 评论
    @RequestMapping(value = "/post/comment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> newComment(@RequestBody Map<String,Object> newRequest){
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        try{

            //need to discuss
            //Comment comment = commentService.createComment(newRequest.get("userName"), newRequest.get("postID"), newRequest.get("text"));
            //end

            publishService.publishComment(newRequest.get("userName"), newRequest.get("postID"), newRequest.get("text"));

            data.put("msg", "success");
            response = getResonse(200, data);
            return response;
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
            return response;
        }
    }

    // 删除Post
    @RequestMapping(value = "/post/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> deletePost(@PathVariable(name = "id", required = true) Integer id) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        try{

             //need to discuss
            //postService.deletePost(id);
            //end

            data.put("msg", "success");
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }

    }

    //查看动态（时间顺序）
    @RequestMapping(value = {"/posts/time/{limit}/{start}/{comments}","/posts",
        "/posts/time/{limit}","/post/time/{limit}/{start}"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPostByTime(@PathVariable(name = "limit",required = false) Integer limit,
                                            @PathVariable(name = "start",required = false) Integer start,
                                            @PathVariable(name = "comments",required = false) Integer comments) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        ArrayList<Post> postList = new ArrayList<Post>();
        try{
            if(limit == null) limit = 10;
            if(start == null) start = 1;
            if(comments == null) comments = 10;

             //need to discuss
            postList = searchService.getPostByTime(limit,start,comments);
            //end

            data.put("downloadCount", postList.size());
            data.put("posts", postList);
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }

    }

     // 查看动态（按照所在地）
    @RequestMapping(value = "/posts/location", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getPostByLocation(@RequestBody Map<String,Object> newRequest){
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        ArrayList<Post> postList = new ArrayList<Post>();
        try{
            Map<String,Object> locationMap = (Map<String,Object>)newRequest.get("location");
            //need to discuss
            String location = locationMap.get("long").toString()+" "+locationMap.get("lat").toString();
            postList = searchService.getPostByLocation(location, newRequest.get("limit"), newRequest.get("range"));
            //end

            data.put("downloadCount", postList.size());
            data.put("postList", postList);
            response = getResonse(200, data);
            return response;
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
            return response;
        }
    }

    //搜索动态
    @RequestMapping(value = {"/posts/search/{userid}/{keyword}/{comments}",
        "/posts/search/{userid}","/posts/search/{userid}/{keyword}"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchPost(@PathVariable(name = "userid",required = true) Integer userid,
                                        @PathVariable(name = "keyword",required = false) String keyword,
                                        @PathVariable(name = "comments",required = false) Integer comments) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        ArrayList<Post> postList = new ArrayList<Post>();
        try{
            if(userid == null) userid = -1;
            if(comments == null) comments = 10;
            if(keyword == null || keyword.equals("#")) keyword = null;
            //need to discuss
            postList = searchService.searchPost(userid, keyword, comments);
            //end

            data.put("downloadCount", postList.size());
            data.put("posts", postList);
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }

    }
}
