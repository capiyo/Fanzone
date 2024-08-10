package ke.co.capiyo.fanzone.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class BulkPaymentRequest implements Serializable {

    final static long serialVersionUID = -8816912412731187317L;
    @SerializedName("OriginatorConversationID")
    @Expose
    private String originatorConversationID;
    @SerializedName("InitiatorName")
    @Expose
    private String initiatorName;
    @SerializedName("SecurityCredential")
    @Expose
    private String securityCredential;
    @SerializedName("CommandID")
    @Expose
    private String commandID;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("PartyA")
    @Expose
    private String partyA;
    @SerializedName("PartyB")
    @Expose
    private String partyB;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("QueueTimeOutURL")
    @Expose
    private String queueTimeOutURL;
    @SerializedName("ResultURL")
    @Expose
    private String resultURL;
    @SerializedName("Occassion")
    @Expose
    private String occassion;

    /**
     * No args constructor for use in serialization
     */
    public BulkPaymentRequest() {
    }

    @Override
    public String toString() {
        return "BulkPaymentRequest{" +
                "originatorConversationID='" + originatorConversationID + '\'' +
                ", initiatorName='" + initiatorName + '\'' +
                ", securityCredential='" + securityCredential + '\'' +
                ", commandID='" + commandID + '\'' +
                ", amount=" + amount +
                ", partyA='" + partyA + '\'' +
                ", partyB='" + partyB + '\'' +
                ", remarks='" + remarks + '\'' +
                ", queueTimeOutURL='" + queueTimeOutURL + '\'' +
                ", resultURL='" + resultURL + '\'' +
                ", occassion='" + occassion + '\'' +
                '}';
    }

    public BulkPaymentRequest(String originatorConversationID, String initiatorName, String securityCredential, String commandID, String amount, String partyA, String partyB, String remarks, String queueTimeOutURL, String resultURL, String occassion) {
        this.originatorConversationID = originatorConversationID;
        this.initiatorName = initiatorName;
        this.securityCredential = securityCredential;
        this.commandID = commandID;
        this.amount = amount;
        this.partyA = partyA;
        this.partyB = partyB;
        this.remarks = remarks;
        this.queueTimeOutURL = queueTimeOutURL;
        this.resultURL = resultURL;
        this.occassion = occassion;
    }

    public String getOriginatorConversationID() {
        return originatorConversationID;
    }

    public void setOriginatorConversationID(String originatorConversationID) {
        this.originatorConversationID = originatorConversationID;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public String getSecurityCredential() {
        return securityCredential;
    }

    public void setSecurityCredential(String securityCredential) {
        this.securityCredential = securityCredential;
    }

    public String getCommandID() {
        return commandID;
    }

    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getQueueTimeOutURL() {
        return queueTimeOutURL;
    }

    public void setQueueTimeOutURL(String queueTimeOutURL) {
        this.queueTimeOutURL = queueTimeOutURL;
    }

    public String getResultURL() {
        return resultURL;
    }

    public void setResultURL(String resultURL) {
        this.resultURL = resultURL;
    }

    public String getOccassion() {
        return occassion;
    }

    public void setOccassion(String occassion) {
        this.occassion = occassion;
    }

}