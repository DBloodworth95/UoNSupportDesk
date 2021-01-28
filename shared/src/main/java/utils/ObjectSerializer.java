package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectSerializer {

    public static byte[] serialize(Object objectToSerialize) {
        byte[] serializedObject = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(objectToSerialize);
            objectOutputStream.flush();
            serializedObject = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serializedObject;
    }
}
