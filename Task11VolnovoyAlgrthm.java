package Homework4;

//Реализовать волновой алгоритм

import java.util.*;

public class Task11VolnovoyAlgrthm {

    //размеры поля
    static int IMAX = 7;
    static int JMAX = 9;

    static boolean find = false;//дошли до финиша или нет
    static boolean[][] isOpen = new boolean[IMAX + 1][JMAX + 1]; // массив открытых/закрытых полей
    //игровое поле   -1 - перегородка лабиринта
   static int num[][] = {{0,0, 0, 0, 0, -1, -1, -1, -1, 0},
                         {0, 0, 0, 0, -1, -1, 0, 0, 0, 0},
                         {0, 0, 0, 0, -1, -1, 0, -1, 0, 0},
                         {0, 0, 0, 0, 0, -1, 0, -1, 0, 0},
                         {-1, 0, -1, -1, -1, -1, -1, 0, 0, 0},
                         {0, 0, 0, 0, 0, -1, 0, 0, 0, 0},
                         {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, -1, -1, 0, 0, 0, 0, 0 }};
   //координаты старта и искомого поля
    static int startJ = 2;
    static int startI = 3;
    static int finishJ = 5;
    static int finishI = 7;

    static int i ;
    static int j;
//последовательно открываем поля
    static void openFields( int delta) {
        while (i>=0 && i<=IMAX) {
            while (j > 0) {
                j = j - 1;
                openOneField(i, j);
            }
            j = startJ ;
            while (j < JMAX) {
                j = j + 1;
                openOneField(i, j);
            }
            checkLine(i);
//            if (find == true) break;
            i = i + delta;
        }
    }
    // проверяем поля в ряде и поправляем их значение при необходимости
    static void checkLine(int i){
        for (int j = 0; j<JMAX; j++) {
            if (Math.abs(num[i][j] - num[i][j + 1]) > 1) {
                openOneField(i, j);
                openOneField(i, j + 1);
            }
        }
            for (int j= JMAX; j>0; j--){
                if (Math.abs(num[i][j] - num[i][j -1]) > 1) {
                    openOneField(i, j);
                    openOneField(i, j -1);
            }
        }
    }
//Проверяем поля в колонках и поправляем их значение при необходимости
    static void checkColumns() {
        for (j = 0; j < JMAX; j++) {
            for (int i = 0; i < IMAX; i++) {
                if (Math.abs(num[i][j] - num[i + 1][j]) > 1) {
                    openOneField(i, j);
                    openOneField(i+1, j);
                }
            }
        }
        //в обратную сторону тоже необходимо пройтись
        for (j = JMAX; j > 0; j--) {
            for (int i = IMAX; i >0 ; i--) {
                if (Math.abs(num[i][j] - num[i - 1][j]) > 1) {
                    openOneField(i, j);
                    openOneField(i-1, j);
                }
            }
        }
    }
// открываем одно поле и присваиваем ему значение в зависимости от значения 4-х соседних полей
   static void openOneField(int i, int j) {
        if (inRange(i, j)) {
          int min = 100500;
         if (inRange(i - 1, j) && isOpen[i - 1][j] && num[i - 1][j] < min) min = num[i - 1][j];
          if (inRange(i + 1, j) && isOpen[i + 1][j] && num[i + 1][j] < min) min = num[i + 1][j];
           if (inRange(i, j - 1) && isOpen[i][j - 1] && num[i][j - 1] < min) min = num[i][j - 1];
            if (inRange(i, j + 1) && isOpen[i][j + 1] && num[i][j + 1] < min) min = num[i][j + 1];

            num[i][j] = min + 1;
            isOpen[i][j] = true;
            if (i == finishI && j == finishJ) find = true;
        }
    }
    // проверяем координату (i,J) на нахождение в поле и что она не является перегородкой (не равна -1)
    static boolean inRange(int i, int j){

        if (i>=0 &&i <=IMAX && j>=0 && j<=JMAX) {
            if (num[i][j] == -1) return false;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        printArr(num);//печатаем исходый массив
        i = startI;
        j = startJ;
        isOpen[i][j] = true;
        openFields(-1);
        if (find == false) {
            i = startI + 1;
            openFields(1);
        }
        checkColumns();
        checkColumns();

        System.out.println("****************");
        printArr(num);
        if (find == true && num[finishI][finishJ] < 100500) {
            System.out.println("До искомого поля можно дойти за " + num[finishI][finishJ] + " шагов");


            List<Integer> list = new ArrayList<>();//формируем список координат пути
            int k = finishI;
            int l = finishJ;
            list.add(l);list.add(k);
            for (int i = num[finishI][finishJ]; i >0; i--) {
                int coord[] = checkArround(k, l);
                list.add(coord[1]);
                list.add(coord[0]);
                k = coord[0];
                l = coord[1];
            }
            Collections.reverse(list);
            printCoord(list);

        }
        else System.out.println("До искомого поля не добраться");
    }
// выбираем координаты кратчайшего пути
    static int[] checkArround(int i, int j){
        int compare= num[i][j];
        int x = 0; int y = 0; int coord [] = new int[2];

            if (inRange(i - 1, j) && isOpen[i - 1][j] && num[i - 1][j] == compare -1) {x = i-1;y = j;}
            if (inRange(i + 1, j) && isOpen[i + 1][j] && num[i + 1][j]  == compare -1) {x = i+1;y = j;}
            if (inRange(i, j - 1) && isOpen[i][j - 1] && num[i][j - 1] == compare -1) {x = i;y = j-1;}
            if (inRange(i, j + 1) && isOpen[i][j + 1] && num[i][j + 1] == compare -1) {x = i;y = j+1;}

            coord[0]=x;
            coord[1]=y;
            return coord;   }

    static void printArr(int[][] arr) {
        for (int[] row : arr) {
            System.out.println(Arrays.toString(row));
        }
    }
    static void printCoord(List<Integer> list) {
        System.out.println("Коордиаты пути: ");
        for (int i= 0 ; i<list.size()-2; i=i+2){
            System.out.print("[" + list.get(i) + ',' + list.get(i+1) + "]" + " -> ");
        }
        int x = list.get(list.size()-2); int y = list.get(list.size()-1);
        System.out.print("[" + x + "," + y + "]");

        }

}