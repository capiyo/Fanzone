package ke.co.capiyo.fanzone.util;

public class Constants {
    public static final int CONNECT_TIMEOUT = 60 * 1000;

    public static final int READ_TIMEOUT = 60 * 1000;

    public static final int WRITE_TIMEOUT = 60 * 1000;

    public static final String BASE_URL = "https://api.safaricom.co.ke/";
    public static final String SANDBOX_BASE_URL = "https://sandbox.safaricom.co.ke/";

    public static final String BUSINESS_SHORT_CODE = "4130051"; //600977, 174379
    public static final String PASSKEY = "49916b7d9e46f31b451e897f4c382fc6c3b9542b7c882c9efc2d07e1f420028f";
    //"bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String CONSUMER_KEY = "3R0GNCMQnlP3C8ZSQZMTNXroJNrPvk1gd5bExKhCMFSHRnEH";
    public static final String CONSUMER_SECRET = "ZANR4Z35Cna2Pg6uig35HY72ZhI8caAhFtkqifPb34J7pnqxutHRzXdg9fL8A9R7";

    public static final String SANDBOX_CONSUMER_KEY = "vJtSfmbtz8tHuIMTiYsaYokKyR8OAVIqTCEtuGaHcPWGOf8M";
    public static final String SANDBOX_CONSUMER_SECRET = "1u5ZelZelS9jTG4Rwab6rtFWcv5HKP6AUx7KiPyaiDJkz3YCCugPkWOG2b6KDNu6";
    public static final String TRANSACTION_TYPE = "CustomerPayBillOnline";
    public static final String PARTYB = "4130051"; //same as business shortcode above // 600977, 4130051
    public static final String CALLBACKURL = "https://api-5hrvecohxa-uc.a.run.app/myCallbackUrl"; // "https://api-f3mczdjw2q-uc.a.run.app/myCallbackUrl"; // "https://us-central1-fanzone-26f22.cloudfunctions.net/api/myCallbackUrl";
    public static final String INITIATOR_NAME = "fanzoneapiuser";
    public static final String INITIATOR_PASSWORD = "j~F7]YLxL004S9?ls6w9"; // "Maino@1010";
    public static final String QUEUE_TIMEOUT_URL = "https://api-5hrvecohxa-uc.a.run.app/queue";
    public static final String RESULT_URL = "https://api-5hrvecohxa-uc.a.run.app/result";
    public static final String B2C_PARTY_A = "3012545";
    // This is the B2C organization shortcode from which the money is sent from. 3012545, 600978, 600584
    public static final String COMMAND_ID = String.valueOf(CommandID.PromotionPayment);

    // generate the security credential => Base64 Encode(OpenSSLEncrypt(Initiator Password + Certificate))
    public static final String SECURITY_CREDENTIAL = "A2PmFbdHOsOOWDj1nynFB9C5/E8M3jLGBopunViq5o6JBGLOqamIMufk4zwM5U0lrnvPbuSQpyfJoEiOGhzBD2aqJPA442UrQ4hEZQwDDadPqEK8cN1WfN5IPfXmOuHsDnuxmCJ5V2bLPA+5ixIA1vQdIz/6DlnkEZEJQae8Jsnde7sgku9KosOuILcJqXE1JEfqMMIoCPtAf3qxp13LtWRxIj38L03vKRc61oBpRS5QZyd5eNQO0AESNJ9CNXQS+6tSVNOUzdwID6u0/ZfBwRF3I8w6/SUCudz7oFr9KJRCNJ3wsBr9Q1g317UlPSUAIdxh05EfrD0tNHCDwH9Wqg==" ;
    // "aS0+KAF7PtsdKmkEzSNAIDQgPRaF5BDIUS0O33ewCUnPDE0zaIhOOWadTqZ1WbYSA+y78cqBbNkruGmuX6ymyAYryaoG/J9q3THT8LsdP5UfUw6rfJGRCzCtWEkQCNwKxUZc0peJ+xSmWUnBBe28m8TsYd+BiKsHunVxMh8ZCYF9yNpTtJIt3xVdnYedRpGtWGByMTfR7zO6E5o/LjsBLC8FSIIrhtZlmif0irYJaVkU9hwySj9RTOTIKbJYkInQL2rFqrIlL+cjgkX7e0KVlPoArwnCsMKResxxsIh7tL1n/hh3vst/KLFgMsEwgscT67uhBklEUrrEiL1t97LIuQ==";
        // "PV/v2VGjp3eEJwYpyrrCV4kiG7KWWN3oWW7eb8YIXhceK8pn2pYmxIiQsasKB5CnSgelijx5Di4zu2Jwzif1cw/ZI3aV6cxTWHZGnc9/M+XMeVQa9rED4EaVSt3z917rcnTHm1CLq94NUFQQW1NUwIW+O8te4vh2W75bHOnLsfI6bwuVq7SiRWX4wg7upzSw7PC13HdvHigh/rcruKfr8VUm2Z4XLcvRCiHg+f8vc7pDwEgBZoiHD3ha/3pIiO8CPi5AwakkKo3vH3gG3X7B8u0m2+4ewQz8UJcqBnAI3gO/SCaK/5/vmylzNUkaUNYnPDLUsv7NF3PN2bXyP1+8ZQ==";

}
