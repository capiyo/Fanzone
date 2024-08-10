package ke.co.capiyo.fanzone.Services;

public interface MpesaListener {
    public void sendSuccessful(String amount, String phone, String date, String receipt);
    public void sendFailed(String reason);
}
