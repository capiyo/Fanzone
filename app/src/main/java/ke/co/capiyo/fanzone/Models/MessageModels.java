package ke.co.capiyo.fanzone.Models;








public class MessageModels {
    private String senderid;
    private String receiverid;
    private  String messages;
    private  String statusUrl;
    private  String time;

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    private  String seen;

    public MessageModels() {

    }

    public MessageModels(String seen,String senderid,String statusUrl,String time,String receiverid,String messages, String caption, String sendImages
						) {

        this.messages=messages;
        this.senderid=senderid;
        this.receiverid=receiverid;
       
        this.statusUrl=statusUrl;
        this.time=time;
        this.seen=seen;

    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
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
