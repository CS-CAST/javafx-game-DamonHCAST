/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mostbasicjavafxmove;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;

/**
 *
 * @author damonh1000
 */
public class Player extends Rectangle {

    int health;
    public int direction;
    double speed = 0.2;
    Image pimg = new Image("file:data/orangeship3.png");
    public ImageView picture = new ImageView(pimg);

    public Player(double x, double y) {
        super(x, y, 30, 30);
        health = 20;
        picture.setImage(pimg);
        picture.setX(x);
        picture.setY(y);
        picture.setFitWidth(30);
        picture.setFitHeight(30);
        setFill(Color.TRANSPARENT);     
    }

    public void moveUp() {
        if (this.getY() > 0) {
            this.setY(this.getY() - speed);
            this.picture.setY(this.getY());
        }
        picture.setRotate(0);
    }

    public void moveDown() {
        if (this.getY() < (600 - 15) - 20) {
            this.setY(this.getY() + speed);
            this.picture.setY(this.getY());
        }
        picture.setRotate(180);
    }

    public void moveRight() {
        if (this.getX() < (600 - 15) - 20) {
            this.setX(this.getX() + speed);
            this.picture.setX(this.getX());
        }
        picture.setRotate(90);
    }

    public void moveLeft() {
        if (this.getX() > 0) {
            this.setX(this.getX() - speed);
            this.picture.setX(this.getX());
        }
        picture.setRotate(270);
    }
}

/*direction
    [1]
 [4]   [2]
    [3]
 */
