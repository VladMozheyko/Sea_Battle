import java.awt.*;
import java.io.Serializable;

public class Cell extends Engine {        //Клетка поля

    public int x, y;
    Color color;
    int cellSize;
    boolean set = false;
    boolean yellow = false;

    Cell(int x, int y){
        this.x = x;
        this.y = y;
        color = Color.GRAY;

    }

    public void setYellow(){             //Попадание в корабль
        color = Color.YELLOW;
        yellow = true;
    }

   public boolean isYellow(){
        return yellow;
   }

   public void setRed(){
        color = Color.RED;
   }



    public void setCellSize(int size){   // Изменение размеров для разных панелей
        cellSize = size;
        set = true;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        if(set){
            g.fill3DRect(x * cellSize + 1, y * cellSize + 1,
                   cellSize - 2, cellSize - 2, true);
        }
        else {
            g.fill3DRect(x * Resource.PLAYER_CELL_SIZE + 1, y * Resource.PLAYER_CELL_SIZE + 1,
                    Resource.PLAYER_CELL_SIZE - 2, Resource.PLAYER_CELL_SIZE - 2, true);
        }

    }

}

