package RequestHandlers;

import Models.ForumModel;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatasUpdateRequestHandler extends Thread{
    String request;
    Socket socket;

    public DatasUpdateRequestHandler(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    @Override
    public void run() {
        Pattern pattern = Pattern.compile("#(.*)$");
        Matcher matcher = pattern.matcher(request);
        String newDatasJsonString = matcher.group(1);
        try {
            FileWriter fw = new FileWriter( "src/DataBase/Datas.txt",false);
            fw.write(newDatasJsonString.trim());
            fw.flush();
            fw.close();
            DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
            dos.write("DatasUpdated".getBytes());
            dos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
