package model;

import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private final int X_COUNT;
    private final int Y_COUNT;
    private final int target;

    private int[][] numbers;

    static Random random = new Random();

    public GridNumber(int xCount, int yCount, int target) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.target = target;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.initialNumbers();
    }

    public void initialNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                //todo: update generate numbers method
                numbers[i][j] = random.nextInt(2) == 0 ? 2 : 0;
            }
        }
        int i = random.nextInt(X_COUNT);
        int j = random.nextInt(Y_COUNT);
        numbers[i][j] = 2;
        while (true){
            int i1 = random.nextInt(X_COUNT);
            int j1 = random.nextInt(Y_COUNT);
            if(i1!=i||j1!=j){
                numbers[i1][j1] = 4;
                break;
            }
        }
    }
    //todo: finish the method of four direction moving.
    public void moveRight(int[][] numbers) {
        for(int i=0;i<X_COUNT;i++){
            int[] copy;
            do{
                copy = Arrays.copyOf(numbers[i],Y_COUNT);
                for(int j = Y_COUNT-2;j>=0;j--){
                    if(numbers[i][j+1] == 0){
                        numbers[i][j+1] = numbers[i][j];
                        numbers[i][j] = 0;
                    }
                    else if(numbers[i][j+1]==numbers[i][j]){
                        numbers[i][j+1] += numbers[i][j];
                        numbers[i][j] = 0;
                    }
                }
            } while (!Arrays.equals(copy,numbers[i]));
        }
    }

    public void moveLeft(int[][] numbers) {
        for(int i=0;i<X_COUNT;i++){
            int[] copy;
            do{
                copy = Arrays.copyOf(numbers[i],Y_COUNT);
                for(int j = 1;j<Y_COUNT;j++){
                    if(numbers[i][j-1] == 0){
                        numbers[i][j-1] = numbers[i][j];
                        numbers[i][j] = 0;
                    }
                    else if(numbers[i][j-1]==numbers[i][j]){
                        numbers[i][j-1] += numbers[i][j];
                        numbers[i][j] = 0;
                    }
                }
            } while (!Arrays.equals(copy,numbers[i]));
        }
    }

    public void moveUp(int[][] numbers) {
        for(int j=0;j<Y_COUNT;j++){
            int[] copy = new int[X_COUNT];
            int[] col = new int[X_COUNT];
            do{
                for(int i=0;i<X_COUNT;i++){
                    copy[i] = numbers[i][j];
                }
                for(int i=1;i<X_COUNT;i++){
                    if(numbers[i-1][j]==0){
                        numbers[i-1][j] = numbers[i][j];
                        numbers[i][j] = 0;
                    }
                    else if(numbers[i-1][j]==numbers[i][j]){
                        numbers[i-1][j] += numbers[i][j];
                        numbers[i][j] = 0;
                    }
                }
                for(int i=0;i<X_COUNT;i++){
                    col[i] = numbers[i][j];
                }
            } while (!Arrays.equals(copy,col));
        }
    }

    public void moveDown(int[][] numbers) {
        int[][] origin = new int[X_COUNT][Y_COUNT];
        for(int i=0;i<X_COUNT;i++){
            origin[i] = numbers[i].clone();
        }
        for(int j=0;j<Y_COUNT;j++){
            int[] copy = new int[X_COUNT];
            int[] col = new int[X_COUNT];
            do{
                for(int i=0;i<X_COUNT;i++){
                    copy[i] = numbers[i][j];
                }
                for(int i=X_COUNT-2;i>=0;i--){
                    if(numbers[i+1][j]==0){
                        numbers[i+1][j] = numbers[i][j];
                        numbers[i][j] = 0;
                    }
                    else if(numbers[i+1][j]==numbers[i][j]){
                        numbers[i+1][j] += numbers[i][j];
                        numbers[i][j] = 0;
                    }
                }
                for(int i=0;i<X_COUNT;i++){
                    col[i] = numbers[i][j];
                }
            } while (!Arrays.equals(copy,col));
        }
    }

    public boolean isValid(String s) {
        int[][] origin = new int[X_COUNT][Y_COUNT];
        for(int i=0;i<X_COUNT;i++){
            origin[i] = numbers[i].clone();
        }
        switch (s){
            case "R":
                moveRight(origin);
                break;
            case "L":
                moveLeft(origin);
                break;
            case "U":
                moveUp(origin);
                break;
            case "D":
                moveDown(origin);
                break;
        }
        for(int i=0;i<X_COUNT;i++){
            if(!Arrays.equals(origin[i],numbers[i])){
                return true;
            }
        }
        return false;
    }

    public void generation() {
       while(true){
           int i = random.nextInt(X_COUNT);
           int j = random.nextInt(Y_COUNT);
           if(numbers[i][j]==0){
               numbers[i][j] = random.nextInt(2) == 0 ? 2 : 4;
               break;
           }
       }
    }

    public boolean isVictory() {
        for(int i=0;i<X_COUNT;i++){
            for(int j=0;j<Y_COUNT;j++){
                if(numbers[i][j]==target){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOver() {
        return !(isValid("R") || isValid("L") || isValid("U") || isValid("D"));
    }

    public int getNumber(int i, int j) {
        return numbers[i][j];
    }

    public int[][] getNumbers() {
        return numbers;
    }

    public void printNumber() {
        for (int[] line : numbers) {
            System.out.println(Arrays.toString(line));
        }
    }
}
