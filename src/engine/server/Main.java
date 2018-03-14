package engine.server;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import engine.base.PlayerScene;
import engine.network.*;
import engine.player.ControlledPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start(); // Creates new thread

        List<PlayerData> playerData = new ArrayList<>();


        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(PlayerData.class);
        kryo.register(AddPlayer.class);



        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
//                if (object instanceof SomeRequest) {
//                    SomeRequest request = (SomeRequest)object;
//                    System.out.println(request.text);
//
//                    SomeResponse response = new SomeResponse();
//                    response.text = "Thanks";
//                    connection.sendTCP(response);
//                }

                // A client has just started, and needs to be added to the server logic
                // we need to tell the client his id, so that the client can update for other clients on the screen
                if (object instanceof AddPlayer) {
                    AddPlayer addPlayer = new AddPlayer();
                    addPlayer.id = connection.getID();
                    System.out.println("Connection id: " + addPlayer.id);
                    connection.sendTCP(addPlayer);
                }

                if (object instanceof PlayerData) {
                    PlayerData data = (PlayerData)object;
                }
            }
        });


    }
}
