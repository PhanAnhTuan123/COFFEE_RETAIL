package socket;

import com.example.coffeeretail.dto.Request;
import com.example.coffeeretail.dto.Response;
import com.example.coffeeretail.protocol.ProtocolUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocket {
    private static Socket socket;
    private static InputStream  in;
    private static OutputStream out;

    // Thiết lập kết nối (hoặc tái kết nối)
    private static void connect() throws IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket("localhost", 1010);
            socket.setSoTimeout(5000);
            in  = socket.getInputStream();
            out = socket.getOutputStream();
            System.out.println("Connected to server via ProtocolUtils");
        }
    }

    // Gửi request và nhận response
    public static Response sendRequest(Request req) {
        try {
            connect();
            // Ghi request dưới dạng JSON/frame của ProtocolUtils
            ProtocolUtils.writeRequest(out, req);
            // Đọc response cũng qua ProtocolUtils
            return ProtocolUtils.readResponse(in);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error("I/O error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Error: " + e.getMessage());
        }
    }

    // Đóng kết nối
    public static void close() {
        try {
            if (socket != null) socket.close();
        } catch (IOException ignored) {}
    }
}
