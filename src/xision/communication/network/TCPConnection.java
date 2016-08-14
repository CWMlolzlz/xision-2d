package xision.communication.network;

import xision.communication.AbstractConnection;
import xision.communication.ConnectionEvent;
import xision.communication.Message;
import xision.game.Dynamic;
import xision.game.XisionGame;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Connor on 10/08/2016.
 */
public class TCPConnection extends AbstractConnection implements Dynamic{

    protected final Socket socket;
    private final List<Message> sendBuffer = new ArrayList<>();
    private final List<ConnectionEvent> listenBuffer = new ArrayList<>();
    private final Thread listenThread;
    private final Thread sendThread;

    public TCPConnection(XisionGame game, String host, int port) throws IOException{
        this(game, new Socket(host, port));

    }

    public TCPConnection(XisionGame game, Socket socket){
        this.socket = socket;

        listenThread = new Thread(this::listenLoop);
        sendThread = new Thread(this::sendLoop);

        game.addDynamic(this);

    }

    public void start(){
        if(sendThread.isAlive() && listenThread.isAlive())
            throw new RuntimeException("Client connections are already running");
        if(sendThread.isAlive() != listenThread.isAlive())
            throw new RuntimeException("Critical error. One of the listen or send threads aren't running while the other is");
        sendThread.start();
        listenThread.start();
    }

    private void listenLoop(){
        try{
            ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream());
            while(!socket.isClosed()){
                try{
                    Message m = (Message) in.readObject();
                    //once an object is read
                    synchronized(listenBuffer){
                        listenBuffer.add(new ConnectionEvent<>(socket, m));
                    }
                }catch(ClassNotFoundException e){
                    e.printStackTrace();
                }catch(EOFException | SocketException endOfConnection){ //other end has closed
                    endOfConnection.printStackTrace();
                    socket.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                try{
                    Thread.sleep(1);
                }catch(InterruptedException ignored){}


            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void sendLoop(){
        try{
            ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
            while(!socket.isClosed()){
                if(!sendBuffer.isEmpty()){
                    synchronized(sendBuffer){
                        for(Message m : sendBuffer){
                            try{
                                out.writeObject(m);
                                out.flush();
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                        sendBuffer.clear();
                    }
                }

                try{
                    Thread.sleep(1);
                }catch(InterruptedException ignored){}
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Send loop over");
    }

    public void send(Message m){
        synchronized(sendBuffer){
            sendBuffer.add(m);
        }
    }

    @Override
    public void update(float delta){
        synchronized(listenBuffer){
            listenBuffer.forEach(this::dispatch);
            listenBuffer.clear();
        }
    }

    public void lateUpdate(){
    }

    public Socket getSocket(){
        return socket;
    }


}
