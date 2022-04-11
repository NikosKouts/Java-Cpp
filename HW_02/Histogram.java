package ce326.hw2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Histogram {
    double [] histogramMatrix;
    int total;
    YUVImage YUVImage;

    public Histogram(YUVImage img){
        this.histogramMatrix = new double[236];
        this.total = img.height * img.width;
        this.YUVImage = new YUVImage(img);

        for(int i = 0; i < img.height; i++) {
            for(int j = 0; j < img.width; j++) {
                this.histogramMatrix[img.YUVMatrix[i][j].getY()]++;
            }
        }
    }

    public String toString(){
        StringBuilder line = new StringBuilder();
        int counter = 0;

        for (int i = 0; i < 236; i++){
            line.append("\n");
            line.append(String.format("%3d", i));
            line.append(".(");
            line.append(String.format("%4d", (int) histogramMatrix[i]));
            line.append(")\t");

            counter = (int) histogramMatrix[i];

            if(counter >= 1000){
                do {
                    line.append("#");
                    counter -= 1000;
                }while(counter >= 1000);
            }

            if(counter >= 100){
                do {
                    line.append("$");
                    counter -= 100;
                }while(counter >= 100);
            }

            if(counter >= 10){
                do {
                    line.append("@");
                    counter -= 10;
                }while(counter >= 10);
            }

            if(counter >= 1){
                do {
                    line.append("*");
                    counter -= 1;
                }while(counter >= 1);
            }
        }
        line.append("\n");

        return line.toString();
    }

    public void toFile(File file){
        try {
            FileWriter myWriter = new FileWriter(file);
            if(file.exists())
                myWriter.write("");

            myWriter.write(toString());
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void equalize(){

            for(int i = 0; i < 236; i++) {
                histogramMatrix[i] = histogramMatrix[i] / total;

                if (i != 0)
                    histogramMatrix[i] = histogramMatrix[i] + histogramMatrix[i - 1];
            }

            for(int j = 0; j < 236; j++){
                histogramMatrix[j] = histogramMatrix[j] * 235;
                histogramMatrix[j] = (double) ((int) histogramMatrix[j]);
            }
    }

   public short getEqualizedLuminocity(int luminocity){
        return (short) (histogramMatrix[luminocity]);
   }
}
