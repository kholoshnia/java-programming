public class Main {
    public static void main(String[] args) {
        short[] f = new short[20]; //Создание массива f типа short
        for (int i = 0; i < f.length; i++)
//Заполнение массива f числами от 1 до 20 в порядке убывания
            f[i] = (short)(f.length - i);

        float[] x = new float[16]; //Создание массива x типа float
        for (int i = 0; i < x.length; i++)
//Заполнение массива x 16-ю случайными числами в диапазоне от -4.0 до 15.0
            x[i] = -4.0f + (float)(Math.random() * 19.0f);

        double[][] h = new double[20][16];

/*Создание массива h типа double (в тексте задания не указан тип, но дана
  точность после запятой, поэтому скорее всего имеется в виду double
  или float. Методы класса Math возвращают double поэтому был выбран именно он)*/

//Заполнение массива h типа double
        for (int i = 0; i < h.length; i++)
            for (int j = 0; j < h[i].length; j++)
                if (f[i] == 16)
                    h[i][j] = Math.cos(Math.pow(Math.cos(x[j]), 1/3));
//Если f[i] = 16
                else {
                    for (int m = 1; m < 20; m+=2)
                        if (f[i] == m) {
                            h[i][j] =  Math.pow(Math.cos(Math.pow((1/2) *
                                    (x[j] - 1), 2)), 2 * Math.pow((2 * ((2/3) /
                                    (x[j] - 0.5))), 2));

//Если f[i] ∈ {1, 3, 4, 6, 8, 9, 11, 17, 18, 19}
                            continue;
                        }

                    h[i][j] = Math.log(Math.sqrt((Math.abs(x[j]) + 1)/
                            Math.abs(x[j]))) * Math.pow((1/3 + Math.atan(Math.pow(Math.E,
                            Math.pow(-Math.pow(Math.cos(x[j]), 2), 1/3)))), 2);
                }

        for (int i = 0; i < h.length; i++) {
//Вывод полученного массива в формате с двумя знаками после запятой
            for (int j = 0; j < h[i].length; j++)
                System.out.printf("%.2f\t", h[i][j]);
            System.out.println();
        }
    }
}