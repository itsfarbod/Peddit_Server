package RequestHandlers;

import Models.DatasModel;
import java.io.*;
import java.net.Socket;

import Models.ForumModel;
import com.google.gson.Gson;
import java.util.ArrayList;
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
                Gson gson = new Gson();
                message = gson.toJson(new DatasModel(new ArrayList<ForumModel>(0)),DatasModel.class);
                FileWriter fw = new FileWriter( "src/DataBase/Datas.txt",false);
                fw.write(message);
                System.out.println("datas sent!");
                fw.flush();
                fw.close();
            }
            DataOutputStream dos =  new DataOutputStream(socket.getOutputStream());
            dos.write(message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
