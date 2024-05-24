import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public client(){
        try{
            System.out.println("Sending request to server");
            
            socket=new Socket("127.0.0.10",7777);
            System.out.println("connection done...");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }
    public void startReading(){
        //thread read 
        Runnable r1=()->{
            System.out.println("reader started...");

            try {
                while(true){
                String msg;
                    msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("server:"+msg);
                }
                }
                 catch (IOException e) {
                    
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
                   

                }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is client");
        new client();
    }
}


// exception handling
// multithreading
// lambda expressions
// PrintWriter
// BufferedReader
// socket programming
// constructors