package ke.co.capiyo.fanzone.Model;

import java.util.Objects;

public class StkCallBack {
    private ke.co.capiyo.fanzone.Model.CallbackMetadata CallbackMetadata;
    private String CheckoutRequestID;
    private String MerchantRequestID;
    private int ResultCode;
    private String ResultDesc;

    public StkCallBack(CallbackMetadata callbackMetadata, String checkoutRequestID, String merchantRequestID, int resultCode, String resultDesc) {
        CallbackMetadata = callbackMetadata;
        CheckoutRequestID = checkoutRequestID;
        MerchantRequestID = merchantRequestID;
        ResultCode = resultCode;
        ResultDesc = resultDesc;
    }

    public CallbackMetadata getCallbackMetadata() {
        return CallbackMetadata;
    }

    public void setCallbackMetadata(CallbackMetadata callbackMetadata) {
        CallbackMetadata = callbackMetadata;
    }

    public String getCheckoutRequestID() {
        return CheckoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        CheckoutRequestID = checkoutRequestID;
    }

    public String getMerchantRequestID() {
        return MerchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        MerchantRequestID = merchantRequestID;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getResultDesc() {
        return ResultDesc;
    }

    public void setResultDesc(String resultDesc) {
        ResultDesc = resultDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StkCallBack that = (StkCallBack) o;
        return ResultCode == that.ResultCode &&
                Objects.equals(CallbackMetadata, that.CallbackMetadata) &&
                Objects.equals(CheckoutRequestID, that.CheckoutRequestID) &&
                Objects.equals(MerchantRequestID, that.MerchantRequestID) &&
                Objects.equals(ResultDesc, that.ResultDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CallbackMetadata, CheckoutRequestID, MerchantRequestID, ResultCode, ResultDesc);
    }

    @Override
    public String toString() {
        return "StkCallback{" +
                "CallbackMetadata=" + CallbackMetadata +
                ", CheckoutRequestID='" + CheckoutRequestID + '\'' +
                ", MerchantRequestID='" + MerchantRequestID + '\'' +
                ", ResultCode=" + ResultCode +
                ", ResultDesc='" + ResultDesc + '\'' +
                '}';
    }
}

