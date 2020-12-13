package lookaroundBackend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import lookaroundBackend.entity.Post;

public class SearchService {
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    /**
     * 若id为null，抛出 IllegalArgumentException
     * 若结果为空，抛出 NoSuchElementException
     * @param id
     * @return
     * @throws Exception
     */
    public Post findPost(Integer id) throws Exception{
        return postService.findPost(id);
    }

    // TODO
	public ArrayList<Post> getPostByTime(Integer limit, Integer start, Integer comments) {
		return null;
	}

    // TODO
	public ArrayList<Post> getPostByLocation(String location, Object object, Object object2) {
		return null;
	}

    // TODO
	public ArrayList<Post> searchPost(Integer userid, String keyword, Integer comments) {
		return null;
	}    
    

}
