package mostbasicjavafxmove;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Platform.exit;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.effect.*;
import javafx.stage.Stage;
import javafx.scene.image.*;

/**
 * For more information see:
 * http://stackoverflow.com/questions/21331519/how-to-get-smooth-animation-with-keypress-event-in-javafx
 * http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
 * http://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
 * https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 */
public class Hernandez_5_MostBasicJavaFXMove extends Application {

    ArrayList<Rectangle> badblockz = new ArrayList();
    ArrayList<String> input = new ArrayList<>();
    Rectangle rect;
    Rectangle box;
    Ellipse circle;
    boolean isAlive = true;
    Player damon;
    Enemy block;

    @Override
    public void start(Stage primaryStage) {

        Image background = new Image("file:moon-2048727_1280.jpg");
        Group root = new Group();
        Scene scene = new Scene(root);

        primaryStage.setTitle("box check");
        primaryStage.setScene(scene);
        Random mcRandy = new Random();

        Canvas canvas = new Canvas(600, 600); //Screen Size
        double CanvasX = canvas.getWidth();
        double CanvasY = canvas.getHeight();

        //Notice gc is not being used yet 
        GraphicsContext gc = canvas.getGraphicsContext2D();

        damon = new Player((CanvasX / 2), (CanvasY / 2));
        block = new Enemy(mcRandy.nextInt(570), mcRandy.nextInt(570));

        //notice we are creating shape objects 
        box = new Rectangle(300, 300, 23, 23);
        box.setFill(Color.PLUM);

        rect = new Rectangle(mcRandy.nextInt(600), mcRandy.nextInt(600),
                25, 25);
        rect.setFill(Color.BLUE);

        circle = new Ellipse(300, 50, 25, 25);
        circle.setFill(Color.ORANGE);
        circle.setEffect(new Glow(10));

        // notice the difference in how an ArrayList adds items 
        badblockz.add(rect);
        badblockz.add(block);

        //add background image while changing the scale to fit window
        ImageView iv1 = new ImageView();
        iv1.setImage(background);
        iv1.setFitHeight(CanvasY);
        iv1.setFitWidth(CanvasX);
        //iv1.setSmooth(true);
        //iv1.setCache(true);

        //we have created an animation timer --- the class MUST be overwritten - look below 
        AnimationTimer timer = new MyTimer();

        for (int i = 0; i < badblockz.size(); i++) {
            System.out.println(i);
        }

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                switch (event.getCode()) {
                    case RIGHT:
                        // don't use toString here!!!
                        damon.moveRight();
                        /*box.setX(box.getX() + 20);
                        box.setFill(Color.CADETBLUE);
                        checkBounds(damon);*/
                        break;
                    case LEFT:
                        damon.moveLeft();
                        /*box.setX(box.getX() - 20);
                        box.setFill(Color.RED);
                        checkBounds(damon);*/
                        break;
                    case UP:
                        damon.moveUp();
                        /*box.setY(box.getY() - 20);
                        box.setFill(Color.GREEN);
                        checkBounds(damon);*/
                        break;
                    case DOWN:
                        damon.moveDown();
                        /*box.setY(box.getY() + 20);
                        box.setFill(Color.ORANGE);
                        checkBounds(damon);*/
                        break;
                    case ESCAPE:
                        exit();
                        break;
                    default:
                        break;
                }
            }
        });

        scene.setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode() == KeyCode.RIGHT) {
                damon.moveRight();
            }
        });

        //try disabling canvas --- notice the difference 
        root.getChildren().add(canvas);
        //notice we are manually adding the shape objects to the "root" window
        root.getChildren().add(iv1);
        //root.getChildren().add(rect);
        //root.getChildren().add(box);
        root.getChildren().add(circle);
        root.getChildren().add(damon);
        root.getChildren().add(block);

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
            doHandle();
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

            if (!isAlive) {
                System.out.println("Animation stopped");
            }
        }
    }

    private void checkBounds(Player box) {
        // checkBounds is called in two different locations --- it's really only
        // necessary in the animation dohandle
        // experiment - check the differences

        boolean collisionDetected = false;
        Random mcRando = new Random();

        // notice the difference in how an ArrayList iterates through items 
        for (Rectangle badblock : badblockz) {
            if (box.getBoundsInParent().intersects( 
                    badblock.getBoundsInParent())) {
                collisionDetected = true;
                badblock.setFill(Color.RED);
            } else {
                badblock.setFill(Color.BLUE);
            }
        }
        if (collisionDetected) {
            block.setFill(Color.RED);
            //isAlive = false;
            block.setX(mcRando.nextInt(570));
            block.setY(mcRando.nextInt(570));
        }
    }
}
/*
  ________
 |  _____ |
 | |  ___||
 | | |_   |
 | |  _|  |
 | |_|    |
 |________|

 */
