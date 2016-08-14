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


    private final Map<Class<? extends Message>,List<ConnectionEventListener<? extends Message>>> bindings = new HashMap<>();

    private final ServerSocket hostSocket;
    private final List<TCPConnection> clients = new ArrayList<>();

    //stores accumulated listeners to attach to new connections
    private final Map<Class<? extends Message>, List<ConnectionEventListener>> listeners = new HashMap<>();

    private final XisionGame game;
    private final Thread thread;

    public Server(XisionGame game, int port, int maxConnections) throws IOException{
        this.hostSocket = new ServerSocket(port);
        this.game = game;
        this.thread = new Thread(this::listenForConnections);
    }

    public void start(){
        if(thread.isAlive()) throw new RuntimeException("Server is already running");
        thread.start();
    }

    public void listenForConnections(){
        while(true){
            // listen for new connections
            try{
                Socket clientSocket = hostSocket.accept();
                TCPConnection connection = new TCPConnection(game, clientSocket);
                for(Map.Entry<Class<? extends Message>, List<ConnectionEventListener>> listenerEntry : listeners.entrySet()){
                    for(ConnectionEventListener l : listenerEntry.getValue()){
                        connection.bind(listenerEntry.getKey(), l);
                    }
                }
                connection.start();
                clients.add(connection);
                sendTo(new StringMessage("Echo"), connection.getSocket());

                Thread.sleep(1);

            }catch(IOException e){
                e.printStackTrace();
            }catch(InterruptedException ignored){}
        }
    }


    @Override
    public <M extends Message> void bind(Class<M> cluzz, ConnectionEventListener<? extends M> listener){

        clients.forEach((client) -> client.bind(cluzz,listener));
        listeners.putIfAbsent(cluzz,new ArrayList<>());
        listeners.get(cluzz).add(listener);
    }

    @Override
    public <M extends Message> void unbind(Class<M> cluzz){
        clients.forEach((client) -> client.unbind(cluzz));
        listeners.remove(cluzz);
    }

    @Override
    public <M extends Message> void unbind(Class<M> cluzz, ConnectionEventListener<? extends M> listener){
        clients.forEach((client) -> client.unbind(cluzz, listener));
        if(listeners.containsKey(cluzz)){
            listeners.get(cluzz).remove(listener);
        }
    }

    @Override
    public void send(Message m){
        clients.forEach((client) -> client.send(m));
    }

    public void sendTo(Message m, Socket socket, Socket... sockets){
        clients.stream().filter(client -> {
            if(client.getSocket().equals(socket)) return true;
            for(Socket s : sockets){ //if its one of the target sockets
                if(client.getSocket().equals(s)) return true;
            }
            return false;
        }).forEach(connection -> connection.send(m)); //send through that connection
    }

    public void sendExclude(Message m, Socket socket, Socket... sockets){
        clients.stream().filter(client -> {
            if(client.getSocket().equals(socket)) return false;
            for(Socket s : sockets){ //if its one of the target sockets
                if(client.getSocket().equals(s)) return false;
            }
            return true;
        }).forEach(client -> client.send(m)); //send through that connection
    }

    /*@Override
    public <M extends Message> void dispatch(ConnectionEvent<? extends M> event){
        //todo: dispatch event to bindings
    }*/
}
