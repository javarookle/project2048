package controller;

import model.GridNumber;
import view.GamePanel;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController {
    private GamePanel view;
    private GridNumber model;


    public GameController(GamePanel view, GridNumber model) {
        this.view = view;
        this.model = model;

    }
    public void restartGame() {
        view.setStartTime(System.currentTimeMillis());
        view.setSteps(0);
        view.getStepLabel().setText("Start");
        view.getStepLabel().setFont(new Font("Serif",Font.ITALIC,22));
        view.getTimeLabel().setText("Start");
        view.getTimeLabel().setFont(new Font("Serif",Font.BOLD,22));
        model.initialNumbers();
        view.updateGridsNumber();
        System.out.println("Do restart game here");
    }
    public void saveGame() {
        int[][] numbers = model.getNumbers();
        try{
            FileWriter writer = new FileWriter("Saving.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for (int[] number : numbers) {
                for (int j = 0; j < number.length; j++) {
                    bufferedWriter.write(Integer.toString(number[j]));
                    if (j < number.length - 1) {
                        bufferedWriter.write(" ");
                    }
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(view.getSteps()));
            bufferedWriter.newLine();
            bufferedWriter.write(Long.toString(view.getTime()));
            System.out.println("Game saved");
            bufferedWriter.close();
            writer.close();
        } catch(IOException e){
            System.out.println("An error occurred");
        }
    }

    public void loadGame(String filePath) {
        int[][] numbers = model.getNumbers();
        boolean isValid = true;
        ArrayList<Integer> input = new ArrayList<>();
        try{
            File file = new File(filePath);
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNext()) {
                String data = fileReader.next();
                try {
                    int num = Integer.parseInt(data);
                    input.add(num);
                } catch (NumberFormatException e) {
                    isValid = false;
                    break;
                }
            }
            if(input.size() != (numbers.length*numbers[0].length+2)){
                isValid = false;
            }
            if(isValid){
                int size = input.size();
                for(int i=0;i<size-2;i++){
                    int num = input.get(i);
                    if(!checkingNum(num)){
                        isValid = false;
                        break;
                    }
                }
                if(!checkingNonZero(input)){
                    isValid = false;
                }
                if(input.get(size-2)<0 || input.get(size-1)<0){
                    isValid = false;
                }
            }
            if(isValid){
                int cnt = 0;
                for (int[] number : numbers) {
                    for (int j = 0; j < number.length; j++) {
                        number[j] = input.get(cnt);
                        cnt ++;
                    }
                }
                int steps = input.get(input.size()-2);
                long time = (long) input.get(input.size()-1);
                view.updateGridsNumber();
                view.setSteps(steps);
                view.getStepLabel().setText(String.format("Steps: %d",steps));
                view.getTimeLabel().setText(String.format("Time: %d",time));
                view.setStartTime(System.currentTimeMillis() - (time + 1) * 1000);
            }
            else {
                System.out.println("Invalid format");
            }
        } catch(FileNotFoundException e){
            System.out.println("Failed to load");
        }
    }

    public boolean checkingNum(int number) {
        if(number<0||number==1){
            return false;
        }
        while (number>1){
            if(number%2 != 0 ){
                return false;
            }
            number = number/2;
        }
        return true;
    }

    public boolean checkingNonZero(ArrayList<Integer> input) {
        for(int i=0;i<input.size()-2;i++){
            int num = input.get(i);
            if(num!=0){
                return true;
            }
        }
        return false;
    }

    //todo: add other methods such as loadGame, saveGame...

}
