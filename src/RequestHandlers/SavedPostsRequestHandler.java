package RequestHandlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SavedPostsRequestHandler extends Thread{
    String request;
    Socket socket;

    public SavedPostsRequestHandler(String request, Socket socket) {
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

    private void sendMessage(String message){
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.write(message.getBytes());
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String userName = matchWith("@(.*?)/");

        try (BufferedReader br = new BufferedReader(new FileReader("src/DataBase/Users.txt"))) {
            String line;
            String UN;
            boolean userFound = false;
            while ((line = br.readLine()) != null) {
                UN=stringMatchWith(line, "\"\\\"userName\\\":\\\"(.*?)\\\"\"");
                if(userName.equals(UN)) {
                    DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                    byte[] messageBytes = line.trim().getBytes("UTF-8");
                    dos.write(messageBytes);
                    dos.close();
                    userFound = true;
                    break;
                }
            }
            if(!userFound) {
                sendMessage("\"UserDidNotfound\"");
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
