package socket;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    // Chỉ khởi tạo khi gọi lần đầu (lazy init)
    private static void connect() throws IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket("localhost", 1010);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server at port 1010");
        }
    }

    // Gửi request và nhận response
    public static Object sendRequest(Object req) throws Exception {
        connect(); // đảm bảo đã kết nối
        out.writeObject(req);
        out.flush();
        return in.readObject();
    }

    // Đóng kết nối nếu cần
    public static void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
