package RequestHandlers;

import Models.ForumListModel;
import Models.ForumModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.net.Socket;
import java.util.ArrayList;

public class AllForumsListRequestHandler extends Thread{

    String request;
    Socket socket;

    public AllForumsListRequestHandler(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    @Override
    public void run() {

        ForumListModel allForums = new ForumListModel();
        ArrayList<ForumModel> allForumsList = new ArrayList<>(0);

        try (BufferedReader br = new BufferedReader(new FileReader("src/DataBase/Forums.txt"))) {
            String line;
            Gson gson = new Gson();
            while ((line = br.readLine()) != null) {
                allForumsList.add(gson.fromJson(line.trim() , ForumModel.class));
            }
            allForums.setForums(allForumsList);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.write(gson.toJson(allForums).getBytes());
            dos.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
