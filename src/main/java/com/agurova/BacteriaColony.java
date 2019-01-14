package com.agurova;

import lombok.Data;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Колония бактерий развивается на двумерной поверхности размером M * N клеток.
 * Каждая бактерия может  иметь соседей,  занимающих соседние клетки по горизонтали, вертикали и диагонали.
 * 1)	Бактерия, имеющая менее 2-х соседей, погибает «от одиночества».
 * 2)	Бактерия, имеющая более  4-х соседей, погибает «от тесноты».
 * 3)	В незанятой клетке, имеющей 3-х соседей,  появляется новая бактерия.
 * 4)	Измения в соседних клетках не влияют на развитие соседних клеток в пределах данного шага.
 */

@Data
@ToString
public class BacteriaColony {
    private int height;
    private int width;

    private final int MAX_NUMBER_NEIGHBOURS_FOR_DEATH = 4;
    private final int MIN_NUMBER_NEIGHBOURS_FOR_DEATH = 2;
    private final int NUMBER_NEIGHBOURS_FOR_BIRTH = 3;

    /**
     * Colony's cells matrix
     */
    public int[][] matrix;

    public BacteriaColony(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Update the matrix according to the rules
     * Get the number of alive cells around every cell and change the cell status
     */
    public void updateColonyMatrix() {
        for (int heightIndex = 0; heightIndex < height; heightIndex++)  //идем сверху вниз
        {
            for (int widthIndex = 0; widthIndex < width; widthIndex++)  //идем слева направо
            {
                int countNeigbours = countNeighbours(heightIndex, widthIndex);

                //считает результат
                if (matrix[heightIndex][widthIndex] == 1) { //если клетка жива
                    if (countNeigbours >= MAX_NUMBER_NEIGHBOURS_FOR_DEATH || countNeigbours <= MIN_NUMBER_NEIGHBOURS_FOR_DEATH)
                        matrix[heightIndex][widthIndex] = 0;  //погибла от тестноты или одиночества
                } else {                          //если клетка мертва
                    if (countNeigbours == NUMBER_NEIGHBOURS_FOR_BIRTH)
                        matrix[heightIndex][widthIndex] = 1;  //родилась новая клетка
                }
            }
        }
    }

    /**
     * Count the number of alive cells around the cell
     * with coordinates "heightIndex" and "widthIndex"
     */
    public int countNeighbours(int heightIndex, int widthIndex){
        int countNeigbours = 0;

        // смотрим левый столбец, если там что-то есть
        if (widthIndex > 0) {
            if (heightIndex > 0)  //по диагонали сверху, если там что-то есть
                countNeigbours += matrix[heightIndex - 1][widthIndex - 1];

            countNeigbours += matrix[heightIndex][widthIndex - 1]; //слева

            if (heightIndex < height - 1)  //по диагонали снизу, если там что-то есть
                countNeigbours += matrix[heightIndex + 1][widthIndex - 1];
        }

        // смотрим средний столбец
        {
            if (heightIndex > 0) //cверху, если там что-то есть
                countNeigbours += matrix[heightIndex - 1][widthIndex];

            if (heightIndex < height - 1) //снизу, если там что-то есть
                countNeigbours += matrix[heightIndex + 1][widthIndex];
        }

        // смотрим правый столбец, если там что-то есть
        if (widthIndex < (width - 1)) {
            if (heightIndex > 0) //по диагонали сверху, если там что-то есть
                countNeigbours += matrix[heightIndex - 1][widthIndex + 1];

            countNeigbours += matrix[heightIndex][widthIndex + 1]; //справа

            if (heightIndex < height - 1) //по диагонали снизу, если там что-то есть
                countNeigbours += matrix[heightIndex + 1][widthIndex + 1];
        }
        return countNeigbours;
    }

    /**
     * Display the colony on screen
     */

    public void printColony() {
        for (int heightIndex = 0; heightIndex < height; heightIndex++)  //идем сверху вниз
        {
            for (int widthIndex = 0; widthIndex < width; widthIndex++) { //идем слева направо
                System.out.print(matrix[heightIndex][widthIndex] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Set all elements = 0
     */
    public void setZeroMatrix() {
        matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    /**
     * Set random elements (0 or 1)
     */
    public void setRandomMatrix() {
        Random random = new Random();
        matrix = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = random.nextInt(2);
            }
        }
    }


    /**
     * Extra method
     * Read matrix from txt file
     */
    public void readColonyMatrix() {
        matrix = new int[height][width];

        try (FileReader fr = new FileReader("C:\\Users\\Lidiia_Agurova\\IdeaProjects\\TrainingSecondTask\\src\\main\\resources\\colony.txt")) {
            BufferedReader br = new BufferedReader(fr);
            for (int index = 0; index < height; index++) {
                String s = br.readLine();
                System.out.println(s);
                String[] stringArray = s.split(",");
                for (int j = 0; j < width; j++) {
                    int temp = Integer.parseInt(stringArray[j]);
                    matrix[index][j] = temp;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}