package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializer {

    public static Object deserialize(byte[] bytesToDeserialize) {
        Object deserializedObject;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytesToDeserialize);

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            deserializedObject = objectInputStream.readObject();

            return deserializedObject;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
