/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mostbasicjavafxmove;

import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author damonh1000
 */
public class Extra extends Ellipse {

    public Extra(double x, double y) {
        super(x, y, 10, 10);
        this.setFill(Color.ORANGE);
        this.setEffect(new Glow(1));
    }

}
