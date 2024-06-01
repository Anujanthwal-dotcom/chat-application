/**
 * server
 */
import java.net.*;
import java.io.*;
public class server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    //constructor
    public server() {
        try {
            server =new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting...");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    public void startReading(){
        //thread read 
        Runnable r1=()->{
            System.out.println("reader started...");

            try {
            while(true){                                                    //true is for reading until exit message is received
                String msg;
                    msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("client:"+msg);
                }
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }


        };

        new Thread(r1).start();
    }

    public void startWriting(){
        // thread write
        Runnable r2=()->{
            System.out.println("writer started...");
            try {
                while(!socket.isClosed()){
                    BufferedReader br_console=new BufferedReader(new InputStreamReader(System.in));
                    
                    String msg_send=br_console.readLine();
                    
                    if(msg_send.equals("exit")){
                        System.out.println("exited");
                        out.println(msg_send);
                        out.flush();
                        socket.close();
                        break;
                    }

                    out.println(msg_send);
                    out.flush();
                }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("connection is closed");

                }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is server...going to start server");
        new server();
    }
}