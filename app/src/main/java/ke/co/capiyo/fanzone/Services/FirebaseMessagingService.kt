package ke.co.capiyo.fanzone.Services

import android.util.Log
import ke.co.capiyo.fanzone.MainActivity
import ke.co.capiyo.fanzone.Model.MpesaResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson


class FirebaseMessagingService :
        FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("MessagingService", remoteMessage.data.toString())

        val payload = remoteMessage.data["payload"]

        val gson = Gson()

        val mpesaResponse = gson.fromJson(payload, MpesaResponse::class.java)

        Log.d("MessagingServiceSecond", mpesaResponse.toString())

        var id = mpesaResponse.body.getStkCallBack().checkoutRequestID

        if (mpesaResponse.body.getStkCallBack().resultCode != 0) {

            var reason = mpesaResponse.body.getStkCallBack().resultDesc

            MainActivity.mpesaListener.sendFailed(reason)
            Log.d("MessagingServiceThird", "Operation Failed")
        } else {
            Log.d("MessagingServiceThird", "Operation Success")

            val list = mpesaResponse.body.getStkCallBack().callbackMetadata.item

            var receipt = ""
            var date = ""
            var phone = ""
            var amount = ""


            for (item in list) {

                if (item.getName() == "MpesaReceiptNumber") {
                    receipt = item.getValue()
                }
                if (item.getName() == "TransactionDate") {
                    date = item.getValue()
                }
                if (item.getName() == "PhoneNumber") {
                    phone = item.getValue()

                }
                if (item.getName() == "Amount") {
                    amount = item.getValue()
                }

            }
            MainActivity.mpesaListener.sendSuccessful(amount, phone, date, receipt)
            Log.d("MetaData", "\nReceipt: $receipt\nDate: $date\nPhone: $phone\nAmount: $amount")
            //Log.d("NewDate", getDate(date.toLong()))
        }

        FirebaseMessaging.getInstance()
                .unsubscribeFromTopic(id)

    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

}