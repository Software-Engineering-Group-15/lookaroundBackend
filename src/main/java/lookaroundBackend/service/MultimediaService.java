package lookaroundBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lookaroundBackend.dao.MultimediaRepository;
import lookaroundBackend.entity.Multimedia;
import lookaroundBackend.entity.Post;

@Service
public class MultimediaService {
    @Autowired
    MultimediaRepository multimediaRepository;

    // 模拟，需要进一步完善
    FileStoreSimulator fs = new FileStoreSimulator();

    // 设用户上传的多媒体文件为字节数组byte[]
    public Multimedia createMultimedia(Post associatedPost, Byte[] file){
        
        String url = fs.save(file); // 把文件存到文件系统里

        Multimedia newMultimedia = new Multimedia();
        newMultimedia.setUrl(url);
        newMultimedia.setAssociatedPost(associatedPost);
        multimediaRepository.save(newMultimedia);
        return newMultimedia;
    }


    public Multimedia findMultimedia(Integer id){
        return multimediaRepository.findById(id).get();
    }

}


class FileStoreSimulator{
    private static Integer cnt = 0;

    FileStoreSimulator(){}

    public String save(Byte[] file){
        // 假装把文件存到文件系统里
        cnt += 1;
        // 返回文件的url
        return "url_" + cnt;
    }

    public Byte[] find(String url){
        // 假装找到了文件并返回
        return null;
    }

}