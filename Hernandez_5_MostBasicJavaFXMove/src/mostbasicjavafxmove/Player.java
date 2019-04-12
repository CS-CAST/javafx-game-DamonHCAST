/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mostbasicjavafxmove;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author damonh1000
 */
public class Player extends Rectangle{

    int health;
    int velocity;

    public Player(double x, double y) {
        super(x, y, 20, 20);
        this.setFill(Color.MAGENTA);
        this.health = 20;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    
    void moveUp() {
        if (this.getY() > 0) {
            this.setY(this.getY() - 10);
        }
    }

    void moveDown() {
        if (this.getY() < 600 - 23) {
            this.setY(this.getY() + 10);
        }
    }

    void moveRight() {
        if (this.getX() < 600 - 23) {
            this.setX(this.getX() + 10);
        }
    }

    void moveLeft() {
        if (this.getX() > 0) {
            this.setX(this.getX() - 10);
        }
    }
    
}

