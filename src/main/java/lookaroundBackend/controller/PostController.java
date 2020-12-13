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
import lookaroundBackend.service.CommentService;
import lookaroundBackend.service.PostService;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

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
            Post post = postService.findPost(post_id);
            data.put("post", post);
            response = getResonse(200, data);
        }catch(NoSuchElementException e){
            data.clear();
            data.put("msg", "post not found");
            response = getResonse(300, data);
        }catch(Exception e){
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
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
            
            //end

            data.put("msg", "success");
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }

    // 请求动态评论
    @RequestMapping(value = {"/post/comments/{post_id}/{limit}/{start}","/post/comments/{post_id}",
        "/posts/comments/{post_id}/{limit}","/posts/comments/{post_id}/{start}"}, method = RequestMethod.GET)
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
            Comment comment = commentService.findComment(post_id);
            data.put("commentList", commentList);
            //end

            response = getResonse(200, data);
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
        }finally{
            return response;
        }
    }

    // 点赞
    @RequestMapping(value = "/post/favor", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> newFavor(@RequestBody Integer id){
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        try{

            //need to discuss
            //commentSerive.favor(id);
            //end

            data.put("msg", "success");
            response = getResonse(200, data);
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
        }finally{
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

            data.put("msg", "success");
            response = getResonse(200, data);
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
        }finally{
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
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }

    //查看动态（时间顺序）
    @RequestMapping(value = {"/posts/time/{limit}/{start}/{comments}","/posts",
        "/posts/{limit}","/posts/{start}","/posts/{comments}","/post/{limit}/{start}",
        "/posts/{limit}/{comments}","/posts/{start}/{comments}"}, method = RequestMethod.GET)
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
            //postList = postService.getPostByTime(limit,start,comments);
            //end

            data.put("downloadCount", postList.size());
            data.put("posts", postList);
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
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
            //postList = postService.getPostByLocation(location, newRequest.get("limit"), newRequest.get("range"));
            //end

            data.put("downloadCount", postList.size());
            data.put("postList", postList);
            response = getResonse(200, data);
        }
        catch(Exception e){
            data.clear();
            data.put("msg",e.getMessage());
            response = getResonse(300, data);
        }finally{
            return response;
        }
    }

    //搜索动态
    @RequestMapping(value = {"/posts/search/{userid}/{keyword}/{comments}",
        "/posts/search/{userid}","/posts/search/{keyword}","/posts/search/{userid}/{keyword}",
        "/posts/search/{userid}/{comments}","/posts/search/{keyword}/{comments}"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchPost(@PathVariable(name = "userid",required = false) Integer userid,
                                        @PathVariable(name = "keyword",required = false) String keyword,
                                        @PathVariable(name = "comments",required = false) Integer comments) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        ArrayList<Post> postList = new ArrayList<Post>();
        try{
            if(userid == null) userid = -1;
            if(comments == null) comments = 10;
             //need to discuss
            //postList = postService.searchPost(userid, keyword, comments);
            //end

            data.put("downloadCount", postList.size());
            data.put("posts", postList);
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }
}
