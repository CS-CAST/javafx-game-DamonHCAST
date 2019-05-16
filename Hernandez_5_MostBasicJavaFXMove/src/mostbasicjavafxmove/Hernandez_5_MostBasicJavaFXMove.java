package mostbasicjavafxmove;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Platform.exit;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.effect.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;

/**
 * For more information see:
 * http://stackoverflow.com/questions/21331519/how-to-get-smooth-animation-with-keypress-event-in-javafx
 * http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
 * http://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
 * https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 */
public class Hernandez_5_MostBasicJavaFXMove extends Application {

    ArrayList<Rectangle> badblockz = new ArrayList();
    ArrayList<Ellipse> coinz = new ArrayList();
    ArrayList<String> input = new ArrayList<>();
    Rectangle startScreen;
    Ellipse circle;
    boolean isAlive = true;
    boolean haveIStarted = false;
    int score;
    Player damon;
    Enemy block;
    Extra point;
    Text t;
    Text a;
    Text d;

    @Override
    public void start(Stage primaryStage) {

        Image background = new Image("file:data/moon-2048727_1280.jpg");
        Group root = new Group();
        Scene scene = new Scene(root);

        primaryStage.setTitle("box check");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(600, 600); //Screen Size
        double CanvasX = canvas.getWidth();
        double CanvasY = canvas.getHeight();

        startScreen = new Rectangle(0, 0, CanvasX, CanvasY);
        startScreen.setFill(Color.GREEN);
        a = new Text(10, 300, "JavaFXMove\nPress A");
        a.setFont(new Font(50));

        //Notice gc is not being used yet 
        GraphicsContext gc = canvas.getGraphicsContext2D();

        damon = new Player((CanvasX / 2), (CanvasY / 2));
        block = new Enemy(0, 0);
        point = new Extra(100, 100);

        //notice we are creating shape objects 
        circle = new Ellipse(300, 50, 25, 25);
        circle.setFill(Color.ORANGE);
        circle.setEffect(new Glow(10));

        // notice the difference in how an ArrayList adds items 
        badblockz.add(block);
        coinz.add(point);

        //add background image while changing the scale to fit window
        ImageView iv1 = new ImageView();
        iv1.setImage(background);
        iv1.setFitHeight(CanvasY);
        iv1.setFitWidth(CanvasX);


        //we have created an animation timer --- the class MUST be overwritten - look below 
        AnimationTimer timer = new MyTimer();

        t = new Text(10, 50, "This is a test.");
        t.setFont(new Font(50));
        t.setFill(Color.GRAY);
        
        d = new Text(10, 590, "You lost! Press A to restart.");
        d.setFont(new Font(20));
        d.setFill(Color.GREEN);

        for (int i = 0; i < badblockz.size(); i++) {
            System.out.println(i);
        }

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A:
                        haveIStarted = true;
                        startScreen.setWidth(0);
                        startScreen.setHeight(0);
                        a.setX(600);
                        d.setX(600);
                        block.respawn();
                        block.picture.setX(block.getX());
                        block.picture.setY(block.getY());
                        block.speed = 0.05;
                        damon.direction = 0;
                        damon.setX(300);
                        damon.setY(300);
                        damon.speed = 0.2;
                        damon.picture.setX(damon.getX());
                        damon.picture.setY(damon.getY());
                        damon.setFill(Color.TRANSPARENT);
                        point.respawn();
                        score = 0;
                        break;
                    case RIGHT:
                        // don't use toString here!!!
                        damon.direction = 2;
                        break;
                    case LEFT:
                        damon.direction = 4;
                        break;
                    case UP:
                        damon.direction = 1;
                        break;
                    case DOWN:
                        damon.direction = 3;
                        break;
                    case ESCAPE:
                        exit();
                    default:
                        break;

                }
            }
        });

        //try disabling canvas --- notice the difference 
        root.getChildren().add(canvas);
        //notice we are manually adding the shape objects to the "root" window
        root.getChildren().add(iv1);
        root.getChildren().add(damon);
        root.getChildren().add(damon.picture);
        root.getChildren().add(block.picture);
        for (Rectangle e : badblockz) {
            root.getChildren().add(e);
        }
        root.getChildren().add(point);
        root.getChildren().add(t);
        root.getChildren().add(startScreen);
        root.getChildren().add(a);
        root.getChildren().add(d);

        timer.start();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     *
     * The same as before main just calls the args described above
     */
    //  vvvvvvvvvvvv   MAIN vvvvvvvvvvv
    public static void main(String[] args) {
        launch(args);
    }

    // ^^^^^^^^^^^  MAIN ^^^^^^^^^^^^^
    // go to youtube to find music use saveclipbro to save videos.
    // ilegal start of expression
    // FBI OPEN UP!
    // we create our time here --- to animate 
    public class MyTimer extends AnimationTimer {

        boolean movedown = true;
        boolean moveright = true;

        // handle is defined by the abstract parent class -- it must be redined 
        // this is what happens again and again until stop()
        @Override
        public void handle(long now) {
            // You can look at the key presses here as well -- this is one of many. Try others
            if (haveIStarted) {
                start();
                doHandle();
            }
            // notice doHandle()  is what happens again and again it's defined below

        }

        private void doHandle() {
            checkBounds(damon);
            if (movedown && block.getY() < 555) {
                block.setY(block.getY() + block.speed);
                block.moveDown();
            }
            if (!movedown && block.getY() > 1) {
                block.setY(block.getY() - block.speed);
                block.moveUp();
            }
            if (moveright && block.getX() < 555) {
                block.setX(block.getX() + block.speed);
                block.moveRight();
            }
            if (!moveright && block.getX() > 1) {
                block.setX(block.getX() - block.speed);
                block.moveLeft();
            }
            if (block.getY() > damon.getY()) {
                movedown = false;
            }
            if (block.getY() < damon.getY()) {
                movedown = true;
            }
            if (block.getX() < damon.getX()) {
                moveright = true;
            }
            if (block.getX() > damon.getX()) {
                moveright = false;
            }
            
            if (damon.direction == 1) {
                damon.moveUp();
            } else if (damon.direction == 3) {
                damon.moveDown();
            }
            if (damon.direction == 2) {
                damon.moveRight();
            } else if (damon.direction == 4) {
                damon.moveLeft();
            }
            
            
            t.setText("" + score);
        }
    }

    private void checkBounds(Player box) { // need to use box for name
        // checkBounds is called in two different locations --- it's really only
        // necessary in the animation dohandle
        // experiment - check the differences

        boolean blockCollisionDetected = false;
        boolean coinCollisionDetected = false;

        // notice the difference in how an ArrayList iterates through items 
        for (Rectangle badblock : badblockz) {
            if (box.getBoundsInParent().intersects(
                    badblock.getBoundsInParent())) {
                blockCollisionDetected = true;
                badblock.setFill(Color.RED);
            } else {
                badblock.setFill(Color.TRANSPARENT);
            }
        }
        for (Ellipse coin : coinz) {
            if (box.getBoundsInParent().intersects(
                    coin.getBoundsInParent())) {
                coinCollisionDetected = true;
            }
        }
        if (blockCollisionDetected) {
            damon.setFill(Color.RED);
            haveIStarted = false;
            d.setX(10);
        }
        if (coinCollisionDetected) {
            point.respawn();
            score++;
            damon.speed += 0.01;
            block.speed += 0.001;
        }
    }
}
/*
  ________
 |  _____ |
 | |  ___||
 | | |_   |
 | |  _|  |
 | |_|    |       **         
 |________|[ ^_^ ]/\[ UwU ]
Don't mind me, just making kaomoji art
 */
