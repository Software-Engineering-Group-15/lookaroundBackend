package lookaroundBackend.JsonBean;

import java.util.HashMap;
import java.util.Map;

public class MessageBean {
    private Integer code;
    private Map<String, String> data = new HashMap<>();
    public MessageBean(Integer code, String msg){
        this.code = code;
        this.data.put("msg", msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
    
}
