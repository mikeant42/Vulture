package engine.base;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import engine.network.AddPlayer;
import engine.network.PlayerData;
import engine.network.SomeRequest;
import engine.network.SomeResponse;

import java.io.IOException;

public class ClientNetworkHandler {

    private Client client;
    private PlayerScene activeScene;
    private int clientID = -1;

    public ClientNetworkHandler() {

        client = new Client();
        client.start();

        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(PlayerData.class);
        kryo.register(AddPlayer.class);


        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }


        AddPlayer req = new AddPlayer(); // We need to add this client to the logic of the server
        req.id = 0; // not the actual id, just used to avoid nullpointer
        client.sendTCP(req);


        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
//                if (object instanceof SomeResponse) {
//                    SomeResponse response = (SomeResponse)object;
//                    System.out.println(response.text);
//                }

                if (object instanceof AddPlayer) {
                    AddPlayer data = (AddPlayer) object;
                    if (clientID != -1) {
                        clientID = data.id;
                    } else {  // This means that we are adding a different client to our logic
                        // TODO check if client is already added
                        activeScene.addNewPlayer(data.id);
                    }
                }

                // If the client recieves playerdata what we want to do is update the client
                if (object instanceof PlayerData) {
                    PlayerData data = (PlayerData)object;
                }
            }
        });

    }

    public int getClientID() {
        return clientID;
    }

    public void setActiveScene(PlayerScene activeScene) {
        this.activeScene = activeScene;
    }

    public void sendPlayerInfo(Object obj) {
        client.sendUDP(obj);
    }

}
