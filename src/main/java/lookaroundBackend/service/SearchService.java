package lookaroundBackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lookaroundBackend.entity.Post;

@Service
@Transactional
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

    // 按时间顺序得到post列表（从新到旧）
	public ArrayList<Post> getPostByTime(Integer limit, Integer start, Integer comments) {
        Iterable<Post> posts = postService.findAllPost();
        List<Post> allPost = new ArrayList<Post>();
        ArrayList returnPost = new ArrayList<Post>();
        posts.forEach(e->{
            allPost.add(e);
        });
        Collections.sort(allPost, new Comparator<Post>(){
            @Override
            public int compare(Post post1, Post post2){
                LocalDateTime time1 = post1.getPublishTime();
                LocalDateTime time2 = post2.getPublishTime();
                return time1.isBefore(time2) ? 1 : -1;
            }
        });
        Integer end = start-1+limit;
        if(start-1 >= allPost.size()) return null;
        if(end >= allPost.size()) end = allPost.size();
        returnPost.addAll(allPost.subList(start-1, end));
        return returnPost;
	}

    // 按地点顺序得到post列表（从近到远）
	public ArrayList<Post> getPostByLocation(String location, Integer limit, Integer range) {
		Iterable<Post> posts = postService.findAllPost();
        List<Post> allPost = new ArrayList<Post>();
        ArrayList returnPost = new ArrayList<Post>();
        posts.forEach(e->{
            allPost.add(e);
        });
        Double longitude = Double.parseDouble(location.split(",")[0]);
        Double lat = Double.parseDouble(location.split(",")[1]);
        Collections.sort(allPost, new Comparator<Post>(){
            @Override
            public int compare(Post post1, Post post2){
                String location1 = post1.getPublishLoction();
                String location2 = post2.getPublishLoction();
                Double longitude1 = Double.parseDouble(location1.split(",")[0]);
                Double lat1 = Double.parseDouble(location1.split(",")[1]);
                Double longitude2 = Double.parseDouble(location2.split(",")[0]);
                Double lat2 = Double.parseDouble(location2.split(",")[1]);
                Double distance1 = (longitude1-longitude)*(longitude1-longitude)+(lat1-lat)*(lat1-lat);
                Double distance2 = (longitude2-longitude)*(longitude2-longitude)+(lat2-lat)*(lat2-lat);
                return (distance1 - distance2) > 0 ? 1 : -1;
            }
        });
        if(limit >= allPost.size()) limit = allPost.size();
        returnPost.addAll(allPost.subList(0, limit));
        return returnPost;
	}

    // TODO
	public ArrayList<Post> searchPost(Integer userid, String keyword, Integer comments) {
		return null;
	}    
    

}
