package ke.co.capiyo.fanzone.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class BulkPaymentResponse implements Serializable {

    private final static long serialVersionUID = 3817826373955208903L;
    @SerializedName("ConversationID")
    @Expose
    private String conversationID;
    @SerializedName("OriginatorConversationID")
    @Expose
    private String originatorConversationID;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseDescription")
    @Expose
    private String responseDescription;


    /**
     * No args constructor for use in serialization
     */
    public BulkPaymentResponse() {
    }

    /**
     * @param responseDescription
     * @param conversationID
     * @param originatorConversationID
     * @param responseCode
     */
    public BulkPaymentResponse(String conversationID, String originatorConversationID, String responseCode, String responseDescription) {
        super();
        this.conversationID = conversationID;
        this.originatorConversationID = originatorConversationID;
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getOriginatorConversationID() {
        return originatorConversationID;
    }

    public void setOriginatorConversationID(String originatorConversationID) {
        this.originatorConversationID = originatorConversationID;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BulkPaymentResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("conversationID");
        sb.append('=');
        sb.append(((this.conversationID == null) ? "<null>" : this.conversationID));
        sb.append(',');
        sb.append("originatorConversationID");
        sb.append('=');
        sb.append(((this.originatorConversationID == null) ? "<null>" : this.originatorConversationID));
        sb.append(',');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null) ? "<null>" : this.responseCode));
        sb.append(',');
        sb.append("responseDescription");
        sb.append('=');
        sb.append(((this.responseDescription == null) ? "<null>" : this.responseDescription));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}