package seria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AA {

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(byteArrayOutputStream);
        User user = new User();
        user.setAge(12);
        user.setName("wx");
        os.writeObject(user);
        byte[] userBytes = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream is = new ByteArrayInputStream(userBytes);
        ObjectInputStream in = new ObjectInputStream(is);
        User user1 = (User) in.readObject();
        System.out.println(user);
        System.out.println(user1);


    }

}
