package server;

// Alex

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.*;

public class Server{
    private ServerSocket server;
    private HashMap<String, Game> Gamelist = new HashMap<String, Game>();
    
    public Server(int port)
    {
        try
        {
            server = new ServerSocket(port);
        } catch (SocketException e) 
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    

    public void start(){
        while(true)
        {
        	Socket client = null;
            try
            {
                System.out.println("Waiting for client at " + server.getLocalPort());
                
                client = server.accept();
                
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream());

                System.out.println("Assigning new thread for player");
                Thread t = new PlayerHandler(client, Gamelist, input, output);
                t.start();
                //output.writeUTF("Greetings");
            }
            catch(Exception e)
            {
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args){
        Server s = new Server(1445);
        s.start();
    };
};