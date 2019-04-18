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
import javafx.scene.text.*;
import javafx.event.ActionEvent;
import javafx.scene.shape.Shape;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
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

    @Override
    public void start(Stage primaryStage) {

        Image background = new Image("file:src/moon-2048727_1280.jpg");
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

        Player damon = new Player(36, 42);

        //notice we are creating shape objects 
        box = new Rectangle(300, 300, 23, 23);
        box.setFill(Color.PLUM);

        rect = new Rectangle(mcRandy.nextInt(600), mcRandy.nextInt(600), 25, 25);
        rect.setFill(Color.BLUE);

        circle = new Ellipse(300, 50, 25, 25);
        circle.setFill(Color.ORANGE);
        circle.setEffect(new Glow(10));

        // notice the difference in how an ArrayList adds items 
        badblockz.add(rect);

        ImageView iv1 = new ImageView();
        iv1.setImage(background);
        iv1.setFitHeight(CanvasY);
        iv1.setFitWidth(CanvasX);
        iv1.setSmooth(true);
        iv1.setCache(true);

        //we have created an animation timer --- the class MUST be overwritten - look below 
        AnimationTimer timer = new MyTimer();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                switch (event.getCode()) {
                    case RIGHT:
                        // don't use toString here!!!
                        damon.moveRight();
                        box.setX(box.getX() + 20);
                        box.setFill(Color.CADETBLUE);
                        checkBounds(box);
                        break;
                    case LEFT:
                        damon.moveLeft();
                        box.setX(box.getX() - 20);
                        box.setFill(Color.RED);
                        checkBounds(box);
                        break;
                    case UP:
                        damon.moveUp();
                        box.setY(box.getY() - 20);
                        box.setFill(Color.GREEN);
                        checkBounds(box);
                        break;
                    case DOWN:
                        damon.moveDown();
                        box.setY(box.getY() + 20);
                        box.setFill(Color.ORANGE);
                        checkBounds(box);
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
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                damon.setVelocity(3);
            }
        });

        //try disabling canvas --- notice the difference 
        root.getChildren().add(canvas);
        //notice we are manually adding the shape objects to the "root" window
        root.getChildren().add(iv1);        
        root.getChildren().add(rect);
        root.getChildren().add(box);
        root.getChildren().add(circle);
        root.getChildren().add(damon);

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
            checkBounds(box);
            if (movedown && rect.getY() < 555) {
                rect.setY(rect.getY() + 0.1);
            }
            if (!movedown && rect.getY() > 1) {
                rect.setY(rect.getY() - 0.1);
            }
            if (moveright && rect.getX() < 555) {
                rect.setX(rect.getX() + 0.1);
            }
            if (!moveright && rect.getX() > 1) {
                rect.setX(rect.getX() - 0.1);
            }
            if (rect.getY() > box.getY()) {
                movedown = false;
            }
            if (rect.getY() < box.getY()) {
                movedown = true;
            }
            if (rect.getX() < box.getX()) {
                moveright = true;
            }
            if (rect.getX() > box.getX()) {
                moveright = false;
            }
            if (!isAlive) {
                stop();
                System.out.println("Animation stopped");
            }
        }
    }

    private void checkBounds(Rectangle box) {
        // checkBounds is called in two different locations --- it's really only
        //necessary in the animation dohandle
        // experiment - check the differences

        boolean collisionDetected = false;

        // notice the difference in how an ArrayList iterates through items 
        for (Rectangle badblock : badblockz) {
            if (box.getBoundsInParent().intersects(badblock.getBoundsInParent())) {
                collisionDetected = true;
                badblock.setFill(Color.RED);
            } else {
                badblock.setFill(Color.BLUE);
            }
        }
        if (collisionDetected) {
            box.setFill(Color.RED);
            isAlive = false;
        }
    }
}
// F
