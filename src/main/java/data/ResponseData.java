package data;

public class ResponseData {

    private Boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public Boolean getSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
private ResponseData(){

}
private ResponseData(String success,User user, String accessToken,String refreshToken){
       this.success = Boolean.valueOf(success);
       this.user = user;
       this.accessToken = accessToken;
        this.refreshToken = refreshToken;
}
}
