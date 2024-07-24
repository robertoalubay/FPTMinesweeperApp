package com.fpt.minesweeper;

import java.util.Random;
import java.util.Scanner;


public class Minesweeper {
    private int[][] cellVisible;
    private int[][] cellHidden;
    int gridSize= 20;

    public static void main(String[] args) {
        Minesweeper M= new Minesweeper();
        M.startGame();
    }

    public void startGame() {
        System.out.println("\n**************** Minesweeper App v1.0! ***************\n");
        
        enterGridSize();
        setupMineLocation();

        boolean flag= true;
        while (flag) {
            showVisible();
            flag= playMove();
            if (checkWin()) {
                showHidden();
                System.out.println("\n**************** You WON!!! ****************");
                break;
            }
        }
    }
        
    public void enterGridSize() {
        int gridSize= 0;
        
        Scanner sc= new Scanner(System.in);
        while (gridSize < 2 || gridSize > 20) {
            System.out.print("Enter Valid Grid Size (2-20): ");
            
            try {
                gridSize= sc.nextInt();
            } catch (Exception ex) {
                sc= new Scanner(System.in);
                continue;
            }
        }
        
        this.gridSize= gridSize;
        
        this.cellVisible= new int[this.gridSize][this.gridSize];
        this.cellHidden= new int[this.gridSize][this.gridSize];
    }

    public void setupMineLocation() {
        int var= 0;
        while (var != this.gridSize) {
            Random random= new Random();
            int i= random.nextInt(this.gridSize);
            int j= random.nextInt(this.gridSize);
            //System.out.println("i: " + i + " j: " + j);
            cellHidden[i][j]= -200;
            var++;
        }
        buildHidden();
    }

    public void buildHidden() {
        for (int i= 0; i < this.gridSize; i++) {
            for (int j= 0; j < this.gridSize; j++) {
                int cnt= 0;
                if (cellHidden[i][j] != -200) {

                    if (i != 0) {
                        if (cellHidden[i - 1][j] == -200)
                            cnt++;
                        if (j != 0) {
                            if (cellHidden[i - 1][j - 1] == -200)
                                cnt++;
                        }

                    }
                    if (i != (this.gridSize - 1)) {
                        if (cellHidden[i + 1][j] == -200)
                            cnt++;
                        if (j != (this.gridSize - 1)) {
                            if (cellHidden[i + 1][j + 1] == -200)
                                cnt++;
                        }
                    }
                    if (j != 0) {
                        if (cellHidden[i][j - 1] == -200)
                            cnt++;
                        if (i != (this.gridSize - 1)) {
                            if (cellHidden[i + 1][j - 1] == -200)
                                cnt++;
                        }
                    }
                    if (j != (this.gridSize - 1)) {
                        if (cellHidden[i][j + 1] == -200)
                            cnt++;
                        if (i != 0) {
                            if (cellHidden[i - 1][j + 1] == -200)
                                cnt++;
                        }
                    }

                    cellHidden[i][j]= cnt;
                }
            }
        }

    }

    public void showVisible() {
        System.out.print("\n\t ");
        
        for (int i= 0; i < this.gridSize; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(i + "  ");
            }
        }
        
        System.out.print("\n");
        for (int i= 0; i < this.gridSize; i++) {
            System.out.print(i + "\t| ");
            for (int j= 0; j < this.gridSize; j++) {
                if (cellVisible[i][j] == 0) {
                    System.out.print("?");
                } else if (cellVisible[i][j] == 50) {
                    System.out.print(" ");
                } else {
                    System.out.print(cellVisible[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

    public boolean playMove() {
        Scanner scCell= new Scanner(System.in);
        int i= 0;
        int j= 0;
        
        try {
            scCell= new Scanner(System.in);
            System.out.print("\nEnter Row Number: ");
            i= scCell.nextInt();
            System.out.print("Enter Column Number: ");
            j= scCell.nextInt();
            
            if (i < 0 || i > (this.gridSize - 1) || j < 0 || j > (this.gridSize - 1) || cellVisible[i][j] != 0) {
                System.out.print("Invalid Row/Column Number...\n");
                return true;
            }
        } catch (Exception ex){
            scCell= new Scanner(System.in);
            System.out.print("Invalid Row/Column Number...\n");
            return true;
        }
        
        if (cellHidden[i][j] == -200) {
            showHidden();
            System.out.print("\n**************** GAME OVER: Mine has been hit! ****************");
            return false;
        } else if (cellHidden[i][j] == 0) {
            fixVisible(i, j);
        } else {
            fixNeighbours(i, j);
        }

        return true;
    }

    public void fixVisible(int i, int j) {
        cellVisible[i][j]= 50;
        if (i != 0) {
            cellVisible[i - 1][j]= cellHidden[i - 1][j];
            if (cellVisible[i - 1][j] == 0)
                cellVisible[i - 1][j]= 50;
            if (j != 0) {
                cellVisible[i - 1][j - 1]= cellHidden[i - 1][j - 1];
                if (cellVisible[i - 1][j - 1] == 0)
                    cellVisible[i - 1][j - 1]= 50;

            }
        }
        if (i != (this.gridSize - 1) ) {
            cellVisible[i + 1][j]= cellHidden[i + 1][j];
            if (cellVisible[i + 1][j] == 0)
                cellVisible[i + 1][j]= 50;
            if (j != (this.gridSize - 1)) {
                cellVisible[i + 1][j + 1]= cellHidden[i + 1][j + 1];
                if (cellVisible[i + 1][j + 1] == 0)
                    cellVisible[i + 1][j + 1]= 50;
            }
        }
        if (j != 0) {
            cellVisible[i][j - 1]= cellHidden[i][j - 1];
            if (cellVisible[i][j - 1] == 0)
                cellVisible[i][j - 1]= 50;
            if (i != (this.gridSize - 1)) {
                cellVisible[i + 1][j - 1]= cellHidden[i + 1][j - 1];
                if (cellVisible[i + 1][j - 1] == 0)
                    cellVisible[i + 1][j - 1]= 50;
            }
        }
        if (j != (this.gridSize - 1)) {
            cellVisible[i][j + 1]= cellHidden[i][j + 1];
            if (cellVisible[i][j + 1] == 0)
                cellVisible[i][j + 1]= 50;
            if (i != 0) {
                cellVisible[i - 1][j + 1]= cellHidden[i - 1][j + 1];
                if (cellVisible[i - 1][j + 1] == 0)
                    cellVisible[i - 1][j + 1]= 50;
            }
        }
    }

    public void fixNeighbours(int i, int j) {
        Random random= new Random();
        int x= random.nextInt() % 4;

        cellVisible[i][j]= cellHidden[i][j];

        if (x == 0) {
            if (i != 0) {
                if (cellHidden[i - 1][j] != -200) {
                    cellVisible[i - 1][j]= cellHidden[i - 1][j];
                    if (cellVisible[i - 1][j] == 0)
                        cellVisible[i - 1][j]= 50;
                }
            }
            if (j != 0) {
                if (cellHidden[i][j - 1] != -200) {
                    cellVisible[i][j - 1]= cellHidden[i][j - 1];
                    if (cellVisible[i][j - 1] == 0)
                        cellVisible[i][j - 1]= 50;
                }

            }
            if (i != 0 && j != 0) {
                if (cellHidden[i - 1][j - 1] != -200) {
                    cellVisible[i - 1][j - 1]= cellHidden[i - 1][j - 1];
                    if (cellVisible[i - 1][j - 1] == 0)
                        cellVisible[i - 1][j - 1]= 50;
                }

            }
        } else if (x == 1) {
            if (i != 0) {
                if (cellHidden[i - 1][j] != -200) {
                    cellVisible[i - 1][j]= cellHidden[i - 1][j];
                    if (cellVisible[i - 1][j] == 0)
                        cellVisible[i - 1][j]= 50;
                }
            }
            if (j != (this.gridSize - 1)) {
                if (cellHidden[i][j + 1] != -200) {
                    cellVisible[i][j + 1]= cellHidden[i][j + 1];
                    if (cellVisible[i][j + 1] == 0)
                        cellVisible[i][j + 1]= 50;
                }

            }
            if (i != 0 && j != (this.gridSize - 1)) {
                if (cellHidden[i - 1][j + 1] != -200) {
                    cellVisible[i - 1][j + 1]= cellHidden[i - 1][j + 1];
                    if (cellVisible[i - 1][j + 1] == 0)
                        cellVisible[i - 1][j + 1]= 50;
                }
            }
        } else if (x == 2) {
            if (i != (this.gridSize - 1)) {
                if (cellHidden[i + 1][j] != -200) {
                    cellVisible[i + 1][j]= cellHidden[i + 1][j];
                    if (cellVisible[i + 1][j] == 0)
                        cellVisible[i + 1][j]= 50;
                }
            }
            if (j != (this.gridSize - 1)) {
                if (cellHidden[i][j + 1] != -200) {
                    cellVisible[i][j + 1]= cellHidden[i][j + 1];
                    if (cellVisible[i][j + 1] == 0)
                        cellVisible[i][j + 1]= 50;
                }

            }
            if (i != (this.gridSize - 1) && j != (this.gridSize - 1)) {
                if (cellHidden[i + 1][j + 1] != -200) {
                    cellVisible[i + 1][j + 1]= cellHidden[i + 1][j + 1];
                    if (cellVisible[i + 1][j + 1] == 0)
                        cellVisible[i + 1][j + 1]= 50;
                }
            }
        } else {
            if (i != (this.gridSize - 1)) {
                if (cellHidden[i + 1][j] != -200) {
                    cellVisible[i + 1][j]= cellHidden[i + 1][j];
                    if (cellVisible[i + 1][j] == 0)
                        cellVisible[i + 1][j]= 50;
                }
            }
            if (j != 0) {
                if (cellHidden[i][j - 1] != -200) {
                    cellVisible[i][j - 1]= cellHidden[i][j - 1];
                    if (cellVisible[i][j - 1] == 0)
                        cellVisible[i][j - 1]= 50;
                }

            }
            if (i != (this.gridSize - 1) && j != 0) {
                if (cellHidden[i + 1][j - 1] != -200) {
                    cellVisible[i + 1][j - 1]= cellHidden[i + 1][j - 1];
                    if (cellVisible[i + 1][j - 1] == 0)
                        cellVisible[i + 1][j - 1]= 50;
                }
            }
        }
    }

    public boolean checkWin() {
        for (int i= 0; i < this.gridSize; i++) {
            for (int j= 0; j < this.gridSize; j++) {
                if (cellVisible[i][j] == 0) {
                    if (cellHidden[i][j] != -200) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void showHidden() {
        System.out.print("\t ");
        
        for (int i= 0; i < this.gridSize; i++) {
            System.out.print(" " + i + "  ");
        }
        
        System.out.print("\n");
        for (int i= 0; i < this.gridSize; i++) {
            System.out.print(i + "\t| ");
            for (int j= 0; j < this.gridSize; j++) {
                if (cellHidden[i][j] == 0) {
                    System.out.print(" ");
                } else if (cellHidden[i][j] == -200) {
                    System.out.print("X");
                } else {
                    System.out.print(cellHidden[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
    
    public int[][] getCellVisible() {
        return this.cellVisible;
    }
    
    public void setCellVisible(int i, int j, int value) {
        this.cellVisible[i][j]= value;
    }
    
    public int[][] getCellHidden() {
        return this.cellHidden;
    }
    
    public void setCellHidden(int i, int j, int value) {
        this.cellHidden[i][j]= value;
    }
    
}
