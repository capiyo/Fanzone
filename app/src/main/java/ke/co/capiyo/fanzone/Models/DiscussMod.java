package ke.co.capiyo.fanzone.Models;








public class DiscussMod {
    private String senderid;
    private String gameId;
    private  String messages;
    private  String statusUrl;
    private  String time;
	private  String username;

    public DiscussMod() {

    }

    public DiscussMod(String username,String senderid,String statusUrl,String time,String receiverid,String messages, String caption, String sendImages
						 ) {

        this.messages=messages;
        this.senderid=senderid;
        this.gameId=gameId;

        this.statusUrl=statusUrl;
        this.time=time;
		this.username=username;

    }

    public String getGameId (){
        return gameId;
    }
	public String getUsername (){
        return username;
    }
	public void setUsername(String username) {
        this.username = username;
    }

    public void setReceiverid(String gameId) {
        this.gameId = gameId;
    }

    public String getSenderid() {
        return senderid;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }










}
