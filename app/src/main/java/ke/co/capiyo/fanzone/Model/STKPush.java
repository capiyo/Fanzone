package ke.co.capiyo.fanzone.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class STKPush implements Serializable {

    @SerializedName("BusinessShortCode")
    @Expose
    public Integer businessShortCode;
    @SerializedName("Password")
    @Expose
    public String password;
    @SerializedName("Timestamp")
    @Expose
    public String timestamp;
    @SerializedName("TransactionType")
    @Expose
    public String transactionType;
    @SerializedName("Amount")
    @Expose
    public Integer amount;
    @SerializedName("PartyA")
    @Expose
    public Long partyA;
    @SerializedName("PartyB")
    @Expose
    public Integer partyB;
    @SerializedName("PhoneNumber")
    @Expose
    public Long phoneNumber;
    @SerializedName("CallBackURL")
    @Expose
    public String callBackURL;
    @SerializedName("AccountReference")
    @Expose
    public String accountReference;
    @SerializedName("TransactionDesc")
    @Expose
    public String transactionDesc;
    private final static long serialVersionUID = 5090870317501155677L;

    /**
     * No args constructor for use in serialization
     *
     */
    public STKPush() {
    }

    /**
     *
     * @param transactionType
     * @param partyA
     * @param password
     * @param amount
     * @param phoneNumber
     * @param callBackURL
     * @param accountReference
     * @param partyB
     * @param businessShortCode
     * @param timestamp
     * @param transactionDesc
     */
    public STKPush(String businessShortCode, String password, String timestamp, String transactionType, String amount, String partyA, String partyB, String phoneNumber, String callBackURL, String accountReference, String transactionDesc) {
        super();
        this.businessShortCode = Integer.valueOf(businessShortCode);
        this.password = password;
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.amount = Integer.valueOf(amount);
        this.partyA = Long.valueOf(partyA);
        this.partyB = Integer.valueOf(partyB);
        this.phoneNumber = Long.valueOf(phoneNumber);
        this.callBackURL = callBackURL;
        this.accountReference = accountReference;
        this.transactionDesc = transactionDesc;
    }

    public Integer getBusinessShortCode() {
        return businessShortCode;
    }

    public void setBusinessShortCode(Integer businessShortCode) {
        this.businessShortCode = businessShortCode;
    }
}