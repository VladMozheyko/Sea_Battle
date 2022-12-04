import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Ship extends Engine {              // Корабль
    int x;
    int y;
    public ArrayList<Coord> positions = new ArrayList<>();
    int len;
    public boolean isSunk = false;

    public Ship(int x, int y, int length, int direction, int p) {
        if(p == 1) {
            for(int i = 0; i < length; i++){
                if(direction == 1) {
                    Resource.computerCoords.add(new Coord(x + i, y));
                    positions.add(new Coord(x + i, y));

                }

                if(direction == 2) {
                    Resource.computerCoords.add(new Coord(x, y + i));
                    positions.add(new Coord(x, y + i));
                }


            }
        }else
            for(int i = 0; i < length; i++){
                if(direction == 1) {
                    Resource.cells.add(new Cell(x + i, y));
                    Resource.playerCoords.add(new Coord(x + i, y));
                    positions.add(new Coord(x + i, y));
                }

                if(direction == 2) {
                    Resource.cells.add(new Cell(x, y + i));
                    Resource.playerCoords.add(new Coord(x, y + i));
                    positions.add(new Coord(x, y + i));
                }

            }
        len = positions.size();
    }

    public void setSunk(){
        isSunk = false;
    }

    public void isAlivePlayer(){
        int count = 0;
        for (int i = 0; i < positions.size(); i++){
            for(int j = 0; j < Resource.computersHits.size(); j++){
                if(positions.get(i).x == Resource.computersHits.get(j).x &&
                        positions.get(i).y == Resource.computersHits.get(j).y){
                    count++;

                }
            }
        }
          if(count == len) {
              for(int i = 0; i < positions.size(); i++){
                  for (int j = 0; j < Resource.computersHits.size(); j++){
                      if(Resource.computersHits.get(j).x == positions.get(i).x &&
                              Resource.computersHits.get(j).y == positions.get(i).y){
                          Resource.computersHits.get(j).setRed();
                      }
                  }
              }
          }
    }




    public void isAliveComputer(){
        int count = 0;
        for (int i = 0; i < positions.size(); i++){
            for(int j = 0; j < Resource.playerHits.size(); j++){
                if(positions.get(i).x == Resource.playerHits.get(j).x &&
                        positions.get(i).y == Resource.playerHits.get(j).y){
                    count++;

                }
            }
        }
        if(count == len) {
            isSunk = true;
            for(int i = 0; i < positions.size(); i++){
                for (int j = 0; j < Resource.playerHits.size(); j++){
                    if(Resource.playerHits.get(j).x == positions.get(i).x &&
                            Resource.playerHits.get(j).y == positions.get(i).y){
                        Resource.playerHits.get(j).setRed();
                    }
                }
            }
        }
    }



    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < Resource.cells.size() ; i++ ) {
            Resource.cells.get(i).draw(g);
        }

    }

}
