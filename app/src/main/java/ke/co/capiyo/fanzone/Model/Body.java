package ke.co.capiyo.fanzone.Model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Body {
    StkCallBack stkCallBack;

    @NonNull
    @Override
    public String toString() {
        return "Body{" +
                "stkCallBack=" + stkCallBack +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Objects.equals(stkCallBack, body.stkCallBack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stkCallBack);
    }

    public StkCallBack getStkCallBack() {
        return stkCallBack;
    }

    public void setStkCallBack(StkCallBack stkCallBack) {
        this.stkCallBack = stkCallBack;
    }
}
