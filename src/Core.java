import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import java.util.ArrayList;
import java.util.Random;

public class Core extends JFrame {
    static Ship ship;                                            //Блок переменных
    Shot shot;
    public static Cell cell;
    boolean hitP = false;
    CanvasPanel leftPanel, playerPanel;

    Random random = new Random();

    static boolean gameOver;
    static String msg;

 static int count = 0;

    public Core() {
        setTitle(Resource.TITLE_OF_PROGRAM);                   //Устанавливаем название игры
        setDefaultCloseOperation(EXIT_ON_CLOSE);               //Закрытие окна
        setResizable(false);                                   //Запрещаем изменение размеров
        createPlayerShips();                                   //Создаем корабли
        createComputerShips();

        leftPanel = new CanvasPanel();                          // Левая панель
        leftPanel.setPreferredSize(new Dimension(Resource.COMPUTER_FIELD_SIZE, Resource.COMPUTER_FIELD_SIZE));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        leftPanel.addMouseListener(new MouseAdapter() {         // Реакция мыши
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX()/40;
                int y = e.getY()/40;
                for(int i = 0; i < Resource.playerShots.size(); i++){
                    if(x == Resource.playerShots.get(i).x && y == Resource.playerShots.get(i).y){
                        return;
                    }
                }
                if(e.getButton() == 1 && !gameOver){
                    System.out.println("left");
                    Resource.playerShots.add(new Shot(x, y, 0));      // Считаем выстрелы игрока
                    hitP = false;
                    if(isHit(x, y, 0)){                               // Если игрок попал
                        cell = new Cell(x, y);
                        cell.setYellow();
                        Resource.playerHits.add(cell);
                        isSunk(1);
                        for(int i = 0; i < Resource.computerShips.size(); i++){
                            if(Resource.computerShips.get(i).isSunk){
                                fillCells(Resource.computerShips.get(i).positions);
                            }
                        }

                        hitP = true;
                        leftPanel.repaint();
                    }
                  if(Resource.playerHits.size() == 20){              // Победа игрока
                      gameOver = true;
                      msg = "Вы победили";
                  }
                  leftPanel.repaint();
                    if(!hitP){                                      // Ответный выстрел
                       answer();
                       if(gameOver){
                           leftPanel.repaint();
                       }
                    }
                }
                leftPanel.repaint();
                playerPanel.repaint();
                if(e.getButton() == 3 && !gameOver){
                    System.out.println("right");
                    shot = new Shot(x, y, 0);
                    shot.switchLabel();

                    if(!isSet(x, y)) {
                        Resource.labels.add(shot);
                    }

                }
            }
        });

        playerPanel = new CanvasPanel();                         // Вторая панель
        playerPanel.setPreferredSize(new Dimension(Resource.PLAYER_FIELD_SIZE, Resource.PLAYER_FIELD_SIZE));
        playerPanel.setBackground(Color.WHITE);
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));


        JButton btnNewGame = new JButton("New Game");            // Кнопки
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resource.computerCoords.clear();
                Resource.playerCoords.clear();
                Resource.computerShots.clear();
                Resource.computersHits.clear();
                Resource.playerHits.clear();
                Resource.playerShots.clear();
                Resource.playerShips.clear();
                Resource.cells.clear();
                Resource.labels.clear();


                createPlayerShips();
                createComputerShips();
                for (int i = 0; i < Resource.computerShips.size(); i++){
                    Resource.computerShips.get(i).setSunk();
                }
                gameOver = false;

                playerPanel.repaint();
                leftPanel.repaint();
            }
        });
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        Resource.log = new JTextArea();
        Resource.log.setEditable(false);
        JPanel bp = new JPanel();
        bp.setLayout(new GridLayout());                                  // Компоновка элементов
        bp.add(btnNewGame);
        bp.add(btnExit);
        rightPanel.add(playerPanel, BorderLayout.NORTH);
        rightPanel.add(bp, BorderLayout.SOUTH);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(leftPanel);
        add(rightPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void fillCells(ArrayList<Coord> coords) {
        int[] Xx = new int[9];
        int[] Yy = new int[9];

        for(int i = 0; i < coords.size(); i++) {

            Xx[0] = coords.get(i).x - 1;
            Xx[1] = coords.get(i).x;
            Xx[2] = coords.get(i).x + 1; //Проверяем верхние координаты
            Yy[0] = coords.get(i).y + 1;
            Yy[1] = coords.get(i).y + 1;
            Yy[2] = coords.get(i).y + 1; //Проверяем верхние координаты

            Xx[3] = coords.get(i).x - 1;
            Xx[4] = coords.get(i).x;
            Xx[5] = coords.get(i).x + 1; //Проверяем боковые кординаты
            Yy[3] = coords.get(i).y;
            Yy[4] = coords.get(i).y;
            Yy[5] = coords.get(i).y;     //Проверяем боковые кординаты

            Xx[6] = coords.get(i).x - 1;
            Xx[7] = coords.get(i).x;
            Xx[8] = coords.get(i). x + 1; //Проверяем нижние координаты
            Yy[6] = coords.get(i).y - 1;
            Yy[7] = coords.get(i).y - 1;
            Yy[8] = coords.get(i).y - 1; //Проверяем нижние координаты

            for(int j = 0; j < 9; j++){
                shot = new Shot(Xx[j], Yy[j], 0);
                Resource.playerShots.add(shot);
            }
        }
    }

    private boolean isSet(int x, int y){
        for(int i = 0; i < Resource.labels.size(); i++){
            if(x == Resource.labels.get(i).x && y == Resource.labels.get(i).y){
                Resource.labels.remove(i);
                return true;
            }
        }
        return false;
    }

    private void answer() {                          // Ответный выстрел
        int Xx = random.nextInt(10);
        int Yy = random.nextInt(10);

        for(int i = 0; i < Resource.computerShots.size(); i++){
           if( Xx == Resource.computerShots.get(i).x && Yy == Resource.computerShots.get(i).y){
                Xx = random.nextInt(10);
                Yy = random.nextInt(10);
                i = 0;
            }
        }

        for(int i = 0; i < Resource.computersHits.size(); i++) {
            if (Xx == Resource.computersHits.get(i).x && Yy == Resource.computersHits.get(i).y){
                Xx = random.nextInt(10);
                Yy = random.nextInt(10);
                i = 0;
            }
        }
        if(isHit(Xx, Yy, 1)){
            cell = new Cell(Xx, Yy);
            cell.setYellow();
            Resource.computersHits.add(cell);
            isSunk(0);


            if(Resource.computersHits.size() == 20){
                gameOver = true;
                msg = "Вы проигали";
                return;
            }
            answer();
        }
        else{
            shot = new Shot(Xx, Yy, 1);
            Resource.computerShots.add(shot);
        }
    }

    private static boolean isHit (int x, int y, int p) {                //Попадание
        if (p == 0) {
            for (int i = 0; i < Resource.computerCoords.size(); i++) {
                if (Resource.computerCoords.get(i).x == x &&
                        Resource.computerCoords.get(i).y == y) {
                    return true;
                }
            }
        }

            if (p == 1) {
                for (int i = 0; i < Resource.playerCoords.size(); i++) {
                    if (Resource.playerCoords.get(i).x == x &&
                            Resource.playerCoords.get(i).y == y) {
                        return true;
                    }
                }
            }

        return false;
    }

    public static void setPlayersCells(Graphics g){                           // Огонь по игроку

        for(int i = 0; i < Resource.computerShots.size(); i++){
            Resource.computerShots.get(i).draw(g);
        }
        for(int i = 0; i < Resource.computersHits.size(); i++){
            Resource.computersHits.get(i);
            Resource.computersHits.get(i).draw(g);
        }
    }

    public static void draw(Graphics g) {                      //Отрисовка огня игрока

        for(int i = 0; i < Resource.playerShots.size(); i++){
            Resource.playerShots.get(i).draw(g);
        }

        for(int i = 0; i < Resource.playerHits.size(); i++){
            Resource.playerHits.get(i).setCellSize(Resource.COMPUTER_CELL_SIZE);
            Resource.playerHits.get(i).draw(g);
        }

        for(int i = 0; i < Resource.labels.size(); i++){
            Resource.labels.get(i).draw(g);
        }

        if(gameOver){
            g.setColor(Color.blue);
            g.setFont(new Font("", Font.BOLD, 40));
            g.drawString(msg, 20, 100);
        }

    }

    private void createComputerShips() {           // Создание кораблей
        int x, y, direction;
        int length = 4;

        x = random.nextInt(7);
        y = random.nextInt(7);
        direction = random.nextInt(2) + 1;
        ship = new Ship(x, y, length, direction, 1);
        Resource.computerShips.add(ship);
        //Создали первый корабль
        while (Resource.computerCoords.size() < 20) {
            if (Resource.computerCoords.size() >= 4 && Resource.computerCoords.size() < 10) {
                length = 3;
                x = random.nextInt(8);
                y = random.nextInt(8);
            }
            if (Resource.computerCoords.size() >= 10  && Resource.computerCoords.size() < 16) {
                length = 2;
                x = random.nextInt(9);
                y = random.nextInt(9);
            }
            if (Resource.computerCoords.size() >= 16) {
                length = 1;
                x = random.nextInt(10);
                y = random.nextInt(10);
            }

            direction = random.nextInt(2) + 1;
            if (isEnableShip(x, y, length, direction, 1)) {
                ship = new Ship(x, y, length, direction, 1);
                Resource.computerShips.add(ship);
            }
        }

    }

    private void createPlayerShips() {
        int x, y, direction;
        int length = 4;

        x = random.nextInt(7);
        y = random.nextInt(7);
        direction = random.nextInt(2) + 1;
        ship = new Ship(x, y, length, direction, 0);
        Resource.playerShips.add(ship);                        //Создали первый корабль
        while (Resource.playerShips.size() < 10) {
            if (Resource.playerShips.size() >= 1 && Resource.playerShips.size() < 3) {
                length = 3;
                x = random.nextInt(8);
                y = random.nextInt(8);
            }
            if (Resource.playerShips.size() >= 3 && Resource.playerShips.size() < 6) {
                length = 2;
                x = random.nextInt(9);
                y = random.nextInt(9);
            }
            if (Resource.playerShips.size() >= 6) {
                length = 1;
                x = random.nextInt(10);
                y = random.nextInt(10);
            }

            direction = random.nextInt(2) + 1;
            if (isEnableShip(x, y, length, direction, 0)) {
                ship = new Ship(x, y, length, direction, 0);
                Resource.playerShips.add(ship);
            }
        }

    }


    public boolean isEnableShip(int x, int y, int length, int direction, int p) {          // Проверка возможности располоить корабль
        for (int i = 0; i < length; i++) {
            ;
            if (direction == 1) {
                if (!isEnableCell(x + i, y, p)) {
                    return false;
                }
            }
            if (direction == 2) {
                if (!isEnableCell(x, y + i, p)) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isEnableCell(int x, int y, int p) {
        int[] Xx = new int[9];
        int[] Yy = new int[9];

        Xx[0] = x - 1;
        Xx[1] = x;
        Xx[2] = x + 1; //Проверяем верхние координаты
        Yy[0] = y + 1;
        Yy[1] = y + 1;
        Yy[2] = y + 1; //Проверяем верхние координаты

        Xx[3] = x - 1;
        Xx[4] = x;
        Xx[5] = x + 1; //Проверяем боковые кординаты
        Yy[3] = y;
        Yy[4] = y;
        Yy[5] = y;     //Проверяем боковые кординаты

        Xx[6] = x - 1;
        Xx[7] = x;
        Xx[8] = x + 1; //Проверяем нижние координаты
        Yy[6] = y - 1;
        Yy[7] = y - 1;
        Yy[8] = y - 1; //Проверяем нижние координаты

        if (p == 0) {
            for (int i = 0; i < Resource.playerCoords.size(); i++) {
                for (int j = 0; j < 9; j++) {
                    if (Resource.playerCoords.get(i).x == Xx[j] && Resource.playerCoords.get(i).y == Yy[j])
                        return false;
                }
            }
        }

        if (p == 1) {
            for (int i = 0; i < Resource.computerCoords.size(); i++) {
                for (int j = 0; j < 9; j++) {
                    if (Resource.computerCoords.get(i).x == Xx[j] && Resource.computerCoords.get(i).y == Yy[j])
                        return false;
                }
            }
        }
        return true;
    }

    public void isSunk(int p){
        if (p == 0) {
            for(int i = 0; i < Resource.playerShips.size(); i++){
               Resource.playerShips.get(i).isAlivePlayer();

            }
        }

        if( p == 1){
            for(int i = 0; i < Resource.computerShips.size(); i++){
                Resource.computerShips.get(i).isAliveComputer();
            }
        }
    }

    public class CanvasPanel extends JPanel {                   // Основа для рисования

        @Override
        public void paint(Graphics g){
            super.paint(g);
            int cellSize = (int)getSize().getWidth()/Resource.CELL_SIZE;
            g.setColor(Color.lightGray);
            for (int i = 0; i < Resource.CELL_SIZE; i++){
                g.drawLine(0, i*cellSize, Resource.CELL_SIZE*cellSize, i*cellSize);
                g.drawLine(i*cellSize, 0, i*cellSize, Resource.CELL_SIZE*cellSize);
            }

            if(cellSize == Resource.PLAYER_CELL_SIZE) {
                Core.ship.draw(g);
                Core.setPlayersCells(g);
            }
            else
                Core.draw(g);
        }
    }
}



