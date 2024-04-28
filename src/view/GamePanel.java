package view;

import model.GridNumber;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends ListenerPanel {
    private final int COUNT = 4;
    private final int target = 2048;
    private GridComponent[][] grids;

    private GridNumber model;
    private JLabel stepLabel;
    private JLabel timeLabel;
    private long startTime;
    private int steps;
    private final int GRID_SIZE;

    public GamePanel(int size) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        this.setSize(size, size);
        this.timeLabel = new JLabel();
        this.GRID_SIZE = size / COUNT;
        this.grids = new GridComponent[COUNT][COUNT];
        this.model = new GridNumber(COUNT, COUNT, target);
        initialGame();

    }

    public GridNumber getModel() {
        return model;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public void setStartTime(long startTime){
        this.startTime = startTime;
    }

    public void initialGame() {
        this.startTime = System.currentTimeMillis();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeLabel.setText(String.format("Time: %d",(System.currentTimeMillis()-startTime)/1000));
            }
        },0,1000);
        this.steps = 0;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j] = new GridComponent(i, j, model.getNumber(i, j), this.GRID_SIZE);
                grids[i][j].setLocation(j * GRID_SIZE, i * GRID_SIZE);
                this.add(grids[i][j]);
            }
        }
        model.printNumber();//check the 4*4 numbers in game
        this.repaint();
    }

    public void updateGridsNumber() {
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j].setNumber(model.getNumber(i, j));
            }
        }
        repaint();
    }


    /**
     * Implement the abstract method declared in ListenerPanel.
     * Do move right.
     */
    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        if(this.model.isValid("R")){
            this.model.moveRight(this.model.getNumbers());
            this.afterMove();
            this.updateGridsNumber();
        }
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if(this.model.isValid("L")) {
            this.model.moveLeft(this.model.getNumbers());
            this.afterMove();
            this.updateGridsNumber();
        }
    }

    @Override
    public void doMoveUp() {
        System.out.println("Click VK_UP");
        if(this.model.isValid("U")){
            this.model.moveUp(this.model.getNumbers());
            this.afterMove();
            this.updateGridsNumber();
        }
    }

    @Override
    public void doMoveDown() {
        System.out.println("Click VK_Down");
        if(this.model.isValid("D")){
            this.model.moveDown(this.model.getNumbers());
            this.afterMove();
            this.updateGridsNumber();
        }
    }

    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        this.model.generation();
        isEnd();
    }

    public void isEnd() {
        if(this.model.isVictory()){
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,400);
                JLabel label = new JLabel("Victory");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                frame.add(label);

                frame.getContentPane().setBackground(Color.WHITE);
                frame.setVisible(true);
            });
        }
        else if(this.model.isOver()){
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,400);
                JLabel label = new JLabel("Game Over");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                frame.add(label);

                frame.getContentPane().setBackground(Color.WHITE);
                frame.setVisible(true);
            });
        }
    }

    public int getSteps() {
        return steps;
    }

    public long getTime() {
        return (System.currentTimeMillis() - this.startTime)/1000;
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public JLabel getStepLabel() {
        return this.stepLabel;
    }

    public JLabel getTimeLabel() {
        return this.timeLabel;
    }

}
