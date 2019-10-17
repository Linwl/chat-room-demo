import com.linwl.chatroomdemo.service.ChatServer;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 15:06
 * @description ：
 * @modified By：
 */
public class ChatRoomApplication {

    public static void main(String[] args)
    {
        new ChatServer(8888).start();
    }
}
