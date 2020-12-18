package lookaroundBackend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
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
import lookaroundBackend.entity.User;
import lookaroundBackend.service.PublishService;
import lookaroundBackend.service.SearchService;
import lookaroundBackend.service.UserManageService;

@RestController
public class PostController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private PublishService publishService;

    @Autowired
    private UserManageService userService;

	private Object put;

    //生成response
    private Map<String,Object> getResonse(Integer code, Map<String,Object> data){
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("code", code);
        response.put("data", data);
        return response;
    }

    //生成postmap
    private Map<String,Object> getPostMap(Post post){
        Map<String,Object> postMap = new HashMap<String,Object>();
        ArrayList<Map<String,Object>> commentlist = new ArrayList<Map<String,Object>>();
        postMap.put("id", post.getId());
        postMap.put("publisher", post.getPublisher().getUsername());
        postMap.put("time", post.getPublishTime().toString());
        postMap.put("location", post.getPublishLoction());
        postMap.put("text", post.getTextContent());
        Set<Comment> commentList = post.getCommentList();
        for(Comment comment:commentList){
            commentlist.add(new HashMap(getCommentMap(comment)));
        }
        postMap.put("commentList", commentlist);
        return postMap;
    }

    //生成commentmap
    private Map<String,Object> getCommentMap(Comment comment){
        Map<String,Object> commentMap = new HashMap<String,Object>();
        commentMap.put("commentID", comment.getId());
        commentMap.put("publisher", comment.getPublisher().getUsername());
        commentMap.put("time", comment.getPublishTime().toString());
        commentMap.put("text", comment.getTextContent());
        return commentMap;
    }
    
    // 按ID获取Post
    @RequestMapping(value = "/post/getmessage/{post_id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPostById(@PathVariable(name = "post_id", required = true) Integer post_id) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>();
        try{
            Post post = searchService.findPost(post_id);
            data.put("post", new HashMap(getPostMap(post)));
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

            String location = locationMap.get("long").toString()+","+locationMap.get("lat").toString();
            User user = userService.findByUsername(publisherMap.get("userName").toString());
            Post post = publishService.publishPost(user, newpost.get("text").toString(), location, new HashSet<Byte[]>());
            
            data.put("post", new HashMap(getPostMap(post)));
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
        ArrayList<Map<String,Object>> commentList = new ArrayList<Map<String,Object>>();
        try{
            if(limit == null) limit = 10;
            if(start == null) start = 1;
            
            //need to discuss
            Post post = searchService.findPost(post_id);

            // TODO: JPA要求用SET实现CommentList。所以limit的要求有些尴尬
            if(post == null) throw new NullPointerException("no post found");
            Set<Comment> commentlist = post.getCommentList();
            if(commentlist == null) data.put("commentList", null);
            else{
                for(Comment comment:commentlist){
                    commentList.add(new HashMap(getCommentMap(comment)));
                }
                data.put("commentList", commentList);
            }
            //end
            data.put("downloadCount", commentList.size());
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
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
            User user = userService.findByUsername(newRequest.get("userName").toString());
            Post post = searchService.findPost(Integer.parseInt(newRequest.get("postID").toString()));
            publishService.publishComment(user, post, newRequest.get("text").toString());
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
        ArrayList<Map<String,Object>> allList= new ArrayList<Map<String,Object>>(); 
        try{
            if(limit == null) limit = 10;
            if(start == null) start = 1;
            if(comments == null) comments = 10;

             //need to discuss
            postList = searchService.getPostByTime(limit,start,comments);
            //end
            if(postList == null) throw new NullPointerException("no post found");
            for(Post post:postList){
                allList.add(new HashMap(getPostMap(post)));
            }
            data.put("downloadCount", allList.size());
            data.put("posts", allList);
            /*for demo
            Map<String,Object> post = new HashMap<String,Object>();
            post.put("id", 3);
            post.put("text", "Hello world");
            post.put("location","39.988,116.310");
            allList.add(new HashMap(post));
            post.clear();
            post.put("id", 4);
            post.put("text", "Hello world");
            post.put("location","40,116.21");
            allList.add(new HashMap(post));
            
            for demo*/
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
        ArrayList<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();
        try{
            Map<String,Object> locationMap = (Map<String,Object>)newRequest.get("location");
            //need to discuss
            String location = locationMap.get("long").toString()+","+locationMap.get("lat").toString();
            postList = searchService.getPostByLocation(location, Integer.parseInt(newRequest.get("limit").toString()), 
                                                        Integer.parseInt(newRequest.get("range").toString()));
            //end
            if(postList == null) throw new NullPointerException("no post found");
            for(Post post:postList){
                allList.add(new HashMap(getPostMap(post)));
            }
            data.put("downloadCount", allList.size());
            data.put("postList", allList);
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
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
        ArrayList<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();
        try{
            if(userid == null) userid = -1;
            if(comments == null) comments = 10;
            if(keyword == null || keyword.equals("#")) keyword = null;
            //need to discuss
            postList = searchService.searchPost(userid, keyword, comments);
            //end
            if(postList == null) throw new NullPointerException("no post found");
            for(Post post:postList){
                allList.add(new HashMap(getPostMap(post)));
            }
            data.put("downloadCount", allList.size());
            data.put("postList", allList);
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }

    }

    //TODO:新增根据用户查找动态的接口，需要search service提供一下实现
    //查看动态（按照用户）
    @RequestMapping(value = "/posts/user/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPostByUser(@PathVariable(name = "username",required = true) String userName) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>();
        ArrayList<Post> postList = new ArrayList<Post>();
        ArrayList<Map<String,Object>> allList= new ArrayList<Map<String,Object>>(); 
        try{
            User user = userService.findByUsername(userName);
             //需要实现
            //postList = searchService.getPostByUser(user);
            
            if(postList == null) throw new NullPointerException("no post found");
            for(Post post:postList){
                allList.add(new HashMap(getPostMap(post)));
            }
            data.put("downloadCount", allList.size());
            data.put("posts", allList);

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
