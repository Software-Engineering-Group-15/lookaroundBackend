package lookaroundBackend.security.fliter.handle;


public class JsonRegisterSuccessHandler extends JsonLoginSuccessHandler {

    private final static String forwardUrl = "/register/success";

    public JsonRegisterSuccessHandler(){
        super(forwardUrl);
    }

}
