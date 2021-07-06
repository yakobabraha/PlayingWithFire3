package server.Client;

import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import org.json.simple.*;

public class Client {
    public JSONObject setPlayer()
    {
        JSONObject player = new JSONObject();

        player.put("ID", "yarro");
        player.put("x", "1");
        player.put("y", "2");
        player.put("health", "100");
        player.put("instruction", "join");

        return player;
    }
	
    public static void main(String[] args)
    {
    	Client client_ = new Client();
        try
        {
            Socket client = new Socket("127.0.0.1", 1445);
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF(client_.setPlayer().toString());

            DataInputStream input = new DataInputStream(client.getInputStream());
            System.out.println(input.readUTF());
            client.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
