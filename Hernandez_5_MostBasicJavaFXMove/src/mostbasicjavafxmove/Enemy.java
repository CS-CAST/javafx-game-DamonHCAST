/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mostbasicjavafxmove;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 *
 * @author damonh1000
 */
public class Enemy extends Rectangle {

    int health;
    double speed = 0.05;
    Image eimg = new Image("file:data/spshipsprite.png");
    ImageView picture = new ImageView(eimg);

    public Enemy(double x, double y) {
        super(x, y, 40, 40);
        health = 20;
        picture.setImage(eimg);
        picture.setX(x);
        picture.setY(y);
        picture.setFitWidth(40);
        picture.setFitHeight(40);
        setFill(Color.TRANSPARENT);
    }

    void moveUp() {
        if (this.getY() > 0) {
            this.setY(this.getY() - speed);
            this.picture.setY(this.getY());
        }
        picture.setRotate(0);
    }

    void moveDown() {
        if (this.getY() < (600 - 15) - 20) {
            this.setY(this.getY() + speed);
            this.picture.setY(this.getY());
        }
        picture.setRotate(180);
    }

    void moveRight() {
        if (this.getX() < (600 - 15) - 20) {
            this.setX(this.getX() + speed);
            this.picture.setX(this.getX());
        }
    }

    void moveLeft() {
        if (this.getX() > 0) {
            this.setX(this.getX() - speed);
            this.picture.setX(this.getX());
        }
    }
    
    void respawn() {
        int respawnPosition = ThreadLocalRandom.current().nextInt(4);
        if (respawnPosition == 1) {
            this.setX(10);
            this.setY(10);
        }
        if (respawnPosition == 2) {
            this.setX(580);
            this.setY(10);
        }
        if (respawnPosition == 3) {
            this.setX(10);
            this.setY(580);
        }
        if (respawnPosition == 4) {
            this.setX(580);
            this.setY(580);
        }
    }
}
