package ke.co.capiyo.fanzone.Model;

import java.util.Objects;

public class MpesaResponse {
    private Body Body;

    public MpesaResponse(Body Body) {
        this.Body = Body;
    }

    public Body getBody() {
        return Body;
    }

    public void setBody(Body Body) {
        this.Body = Body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MpesaResponse that = (MpesaResponse) o;
        return Objects.equals(Body, that.Body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Body);
    }

    @Override
    public String toString() {
        return "MpesaResponse{" +
                "Body=" + Body +
                '}';
    }
}

