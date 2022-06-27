public class ServerShutDowner {
    public static void main(String[] args) {
        Server.setIsServerUp(false);
        System.err.println("Server Shut Downed!");
    }
}
