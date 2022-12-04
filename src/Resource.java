import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Random;

public class Resource {                     // Хранение глобальных переменных
    final static String TITLE_OF_PROGRAM = "Sea battle";
    final static int COMPUTER_FIELD_SIZE = 400;
    final static int CELL_SIZE = 10;
    final static int COMPUTER_CELL_SIZE = COMPUTER_FIELD_SIZE/CELL_SIZE;
    final static int PLAYER_FIELD_SIZE = COMPUTER_FIELD_SIZE/2;
    final static int PLAYER_CELL_SIZE = PLAYER_FIELD_SIZE/CELL_SIZE;


    static JTextArea log;

    static ArrayList<Ship> playerShips = new ArrayList<>();
    static ArrayList<Shot> playerShots = new ArrayList<>();
    static ArrayList<Cell> playerHits = new ArrayList<>();
    static ArrayList<Cell> computersHits = new ArrayList<>();

    static ArrayList<Shot> computerShots = new ArrayList<>();
    static ArrayList<Cell> cells = new ArrayList<>();
    static ArrayList<Coord> playerCoords = new ArrayList<>();
    static ArrayList<Coord> computerCoords = new ArrayList<>();

    static ArrayList<Ship> computerShips = new ArrayList<>();

    static ArrayList<Shot> labels = new ArrayList<>();


}



