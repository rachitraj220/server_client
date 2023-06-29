import java.io.*;
import java.lang.Exception;
import java.net.Socket;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Client(){
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1", 8862);
            System.out.println("Connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
            
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public void startReading(){
        // thread to read data

        Runnable r1 = ()->{
            System.out.println("Reader started..");

            try{
            while(true && !socket.isClosed()){
               
            
                
               String msg = br.readLine();
            

               if(msg.equals("over&out")){
                System.out.println("Server terminated the chat");
                socket.close();
                break;
               }
            

               System.out.println("Server: "+msg);
            }
        }catch(Exception e){
            System.out.println("Connection is closed");
        }
        };
        new Thread(r1).start();
    }

    public void startWriting(){
        // thread to send message to the client

        Runnable r2 = ()->{
            System.out.println("Write...");

            try {
            while(true ){
                
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("over&out")){
                        socket.close();
                        break;

                    }

                    
            }
        } catch (Exception e) {
            //TODO: handle exception

            System.out.println("");
        }

        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("This is client...");
        new Client();
    }
    
}
