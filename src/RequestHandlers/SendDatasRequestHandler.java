package RequestHandlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendDatasRequestHandler extends Thread{
    String request;
    Socket socket;

    public SendDatasRequestHandler(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/DataBase/Datas.txt"))) {
            String message;
            String DatasJson = br.readLine();
            if(DatasJson != null) {
                message = DatasJson.trim();
            } else {
                message = "DatasNotFound";
            }
            DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
            dos.write(message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
