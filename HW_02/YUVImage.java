package ce326.hw2;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class YUVImage {
    int width, height, Y, U, V;
    YUVPixel [][] YUVMatrix;

    public YUVImage(int width, int height){
    YUVMatrix = new YUVPixel[height][width];
        this.width = width;
        this.height = height;

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++)
                YUVMatrix[i][j] = new YUVPixel((short) 16, (short) 128, (short) 128);
        }
    }

    public YUVImage(YUVImage copyImg){
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.Y = copyImg.Y;
        this.U = copyImg.U;
        this.V = copyImg.V;

        this.YUVMatrix = new YUVPixel[height][width];

        for (int i = 0; i < copyImg.height; i++){
            for (int j = 0; j < copyImg.width; j++)
                YUVMatrix[i][j] = new YUVPixel(copyImg.YUVMatrix[i][j]);
        }
    }

    public YUVImage(RGBImage RGBImg){
        this.height = RGBImg.height;
        this.width = RGBImg.width;
        this.YUVMatrix = new YUVPixel[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                YUVMatrix[i][j]=new YUVPixel(RGBImg.RGBMatrix[i][j]);
            }
        }
    }

    public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        try(Scanner fileReader = new Scanner(file)) {
            if(!file.exists()) {
                throw new java.io.FileNotFoundException();
            }

            if (!fileReader.next().equals("YUV3")) {
                throw new ce326.hw2.UnsupportedFileFormatException();
            }

            this.width = fileReader.nextInt();
            this.height = fileReader.nextInt();
            this.YUVMatrix = new YUVPixel[this.height][this.width];

            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++)
                    YUVMatrix[i][j] = new YUVPixel(fileReader.nextShort(),  fileReader.nextShort(),  fileReader.nextShort());
            }

        }catch (FileNotFoundException ex){
            throw new java.io.FileNotFoundException();
        }catch (UnsupportedFileFormatException ex){
            throw new UnsupportedFileFormatException();
        }
    }

    public String toString() {
        StringBuilder line = new StringBuilder();

        line.append("YUV3" + "\n");
        line.append(width).append(" ");
        line.append(height).append("\n");

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                line.append(YUVMatrix[i][j].getY()).append(" ");
                line.append(YUVMatrix[i][j].getU()).append(" ");
                line.append(YUVMatrix[i][j].getV()).append(" ");
            }
            line.append("\n");
        }

        return (line.toString());
    }

    public void toFile(java.io.File file){
        try {
            FileWriter myWriter = new FileWriter(file);
            if(file.exists())
                myWriter.write("");

            myWriter.write(toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void equalize(){
        Histogram histogram = new Histogram(this);
        histogram.equalize();

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                YUVMatrix[i][j].setY(histogram.getEqualizedLuminocity(YUVMatrix[i][j].getY()));
            }
        }
    }
}
