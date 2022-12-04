import java.awt.*;
import java.io.Serializable;

public class Shot extends Engine {           //Выстрел

    int x;
    int y;
    int p;
    boolean label = false;


    public Shot(int x, int y, int p) {
        this.x = x;
        this.y = y;
        this.p = p;
    }

    void switchLabel(){
        label = !label;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        if(label){
            g.drawRect(x * 40 + 40/ 2 - 3, y * 40+ 40 / 2 - 3, 8, 8);
            p = -1;

        }
        if(p == 0) {
            g.fillRect(x * 40 + 40/ 2 - 3, y * 40+ 40 / 2 - 3, 8, 8);

        }

        if(p == 1) {
            g.fillRect(x * 20 + 40/ 4 - 3, y * 20+ 20 / 2 - 3, 8, 8);

        }


    }

}

