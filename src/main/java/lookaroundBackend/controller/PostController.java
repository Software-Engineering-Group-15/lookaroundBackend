package lookaroundBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lookaroundBackend.entity.Post;
import lookaroundBackend.service.PostService;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    // 获取全部Post
    // @RequestMapping(name = "/Post", method = RequestMethod.GET)
    // public List<Post> getAllPost() {
    //     return (List<Post>) postService.findAllPost();
    // }

    // 按ID获取Post
    @RequestMapping(name = "/Post/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable(name = "id", required = true) Integer id) {
        return postService.findPost(id); // 需要异常处理的逻辑
    }

    // 上传Post
    @RequestMapping(name = "/Post", method = RequestMethod.POST)
    public void newPost(@RequestBody Post newPost) {

        // 需要对API进行进一步设计

        // return postService.create(newPost.getPublisher(), newPost.getTextContent());
    }

}
