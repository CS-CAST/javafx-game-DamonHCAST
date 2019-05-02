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
public class Player extends Rectangle{

    int health;
    int direction = 1;
    double speed = 10;
    Image pimg = new Image("file:data/orangeship3.png");
    ImageView picture = new ImageView(pimg);

    public Player(double x, double y) {
        super(x, y, 30, 30);
        health = 20;
        setFill(Color.MAGENTA);
        /*picture.setImage(pimg);
        picture.setX(x);
        picture.setY(y);
        picture.setFitWidth(30);
        picture.setFitHeight(30);*/
    }
    
    void moveUp() {
        direction = 1;
        if (this.getY() > 0) {
            this.setY(this.getY() - speed);
        }
    }

    void moveDown() {
        direction = 3;
        if (this.getY() < (600 - 15) - 20) {
            this.setY(this.getY() + speed);
        }
    }

    void moveRight() {
        direction = 2;
        if (this.getX() < (600 - 15) - 20) {
            this.setX(this.getX() + speed);
        }
    }

    void moveLeft() {
        direction = 4;
        if (this.getX() > 0) {
            this.setX(this.getX() - speed);
        }
    }
    
}

/*direction
    [1]
 [4]   [2]
    [3]
*/

