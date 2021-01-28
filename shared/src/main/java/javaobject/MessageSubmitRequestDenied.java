package javaobject;

import java.io.Serializable;

public class MessageSubmitRequestDenied implements Serializable, ObjectCommand {

    private final String response;

    public MessageSubmitRequestDenied() {
        this.response = "messagesubmitfailed";
    }

    public String getResponse() {
        return response;
    }
}
