package xision.game;

import xision.game.input.Input;
import xision.graphics.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Connor on 6/08/2016.
 */
public final class XisionGame{

    private static final int DEBUG_PADDING = 15;
    private static XisionGame instance;
    public Input input;
    private Set<GameObject> gameObjects = new HashSet<>();
    private Set<Dynamic> dynamics = new HashSet<>();
    private Set<Drawable> drawables = new HashSet<>();
    private boolean showDebugLayer = false;
    private boolean exit = false;
    private Thread thread;
    private JFrame frame;
    private JPanel canvas;
    private int fps = 60;
    private float secondsPerFrame = 1f / fps;

    private XisionGame(String name, int width, int height){

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ignored){}

        frame = new JFrame(name);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                super.windowClosing(e);
                XisionGame.this.exit();
            }
        });

        canvas = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                XisionGame.this.draw(g);
            }
        };

        input = new Input();
        frame.addKeyListener(input);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);

        frame.add(canvas);

        Dimension size = new Dimension(width, height);
        frame.setSize(size);
        frame.setResizable(false);


        //frame.createBufferStrategy(2);
        frame.setVisible(true);

        this.launch();
    }

    public static XisionGame create(int width, int height){
        return create("Xision",width,height);
    }
    public static XisionGame create(String name, int width, int height){
        if(instance != null) throw new RuntimeException("XisionGame has already been created");
        return instance = new XisionGame(name, width, height);
    }

    public static XisionGame instance(){
        if(instance != null) return instance;
        throw new RuntimeException("XisionGame has not been created yet. Create one first");
    }

    public void setDebugLayerVisible(boolean b){
        this.showDebugLayer = b;
    }

    private void launch(){
        this.thread = new Thread(() -> {
            while(!exit){
                XisionGame.this.update();
                try{
                    Thread.sleep((int)(secondsPerFrame * 1000));
                }catch(InterruptedException e){
                    XisionGame.this.exit(1);
                }
            }
            XisionGame.this.exit();
        });
        this.thread.start();
    }

    private void update(){

        for(Dynamic d : dynamics){
            d.update(secondsPerFrame);
        }
        canvas.repaint();
    }

    private void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(Drawable d : drawables){

            //skip parented game objects. they will be drawn via parent
            if(d instanceof GameObject && ((GameObject) d).getParent() != null){
                continue;
            }
            d.draw(g2d);
        }

        if(showDebugLayer){
            drawDebugLayer(g2d);
        }

    }

    private void drawDebugLayer(Graphics2D g){
        g.setColor(Color.BLACK);
        int x = 0;
        int y = 0;
        g.drawString("Mouse: " + this.input.mousePosition().toString(), x += DEBUG_PADDING, y += DEBUG_PADDING);
    }

    private void exit(int i){
        if(exit) throw new RuntimeException("XisionGame is already exiting");
        System.out.println("Shutting down XisionGame");
        System.exit(i);
    }

    public void exit(){
        this.exit(0);
    }

    public void addDrawable(Drawable r){
        if(r == null) throw new RuntimeException("Null is not a valid Drawable");
        drawables.add(r);
    }

    public void addDynamic(Dynamic d){
        if(d == null) throw new RuntimeException("Null is not a valid Dynamic");
        dynamics.add(d);
    }

    public void addGameObject(GameObject go){
        if(go == null) throw new RuntimeException("Null is not a valid GameObject");
        gameObjects.add(go);
        addDrawable(go);
        addDynamic(go);
    }

}
