package xision.communication.network;

import xision.communication.*;
import xision.communication.messages.StringMessage;
import xision.game.XisionGame;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by Connor on 8/08/2016.
 */
public final class Server implements Connection{

    private final ServerSocket hostSocket;
    private final List<NetworkConnection> clients = new ArrayList<>();
    private final XisionGame game;
    private final Thread thread;

    public Server(XisionGame game, int port, int maxConnections) throws IOException{
        this.hostSocket = new ServerSocket(port);
        this.game = game;
        this.thread = new Thread(this::listenForConnections);
    }

    public void connect(){
        if(thread.isAlive()) throw new RuntimeException("Server is already running");
        thread.start();
    }

    public void listenForConnections(){
        while(true){
            // listen for new connections
            try{
                Socket clientSocket = hostSocket.accept();
                NetworkConnection connection = new NetworkConnection(game, clientSocket);
                connection.connect();
                clients.add(connection);
                sendTo(new StringMessage("Echo"), connection.getSocket());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public <M extends Message> void bind(Class<M> cluzz, ConnectionEventListener<? extends M> listener){
        clients.forEach((client) -> client.bind(cluzz,listener));
    }

    @Override
    public <M extends Message> void unbind(Class<M> cluzz){
        clients.forEach((client) -> client.unbind(cluzz));
    }

    @Override
    public <M extends Message> void unbind(Class<M> cluzz, ConnectionEventListener<? extends M> listener){
        clients.forEach((client) -> client.unbind(cluzz, listener));
    }

    @Override
    public void send(Message m){
        clients.forEach((client) -> client.send(m));
    }

    public void sendTo(Message m, Socket... sockets){
        clients.stream().filter(client -> {
            for(Socket s : sockets){ //if its one of the target sockets
                if(client.getSocket().equals(s)) return true;
            }
            return false;
        }).forEach(client -> client.send(m)); //send through that connection
    }

    @Override
    public <M extends Message> void dispatch(ConnectionEvent<? extends M> event){
        //todo: dispatch event to bindings
    }
}
