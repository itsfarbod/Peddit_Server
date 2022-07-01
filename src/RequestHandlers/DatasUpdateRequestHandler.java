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

    private String stringMatchWith(String str, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String matchWith(String regex){
        return stringMatchWith(request, regex);
    }

    @Override
    public void run() {
        String newDatasJsonString = matchWith("#(.*)$");
        System.out.println(newDatasJsonString);
        try {
            FileWriter fw = new FileWriter( "src/DataBase/Datas.txt",false);
            fw.write(newDatasJsonString.trim());
            fw.flush();
            fw.close();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.write("DatasUpdated".getBytes());
            System.out.println("Datas Updated!");
            dos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
