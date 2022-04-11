package ce326.hw2;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PPMImage extends RGBImage{

    public PPMImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {

        try(Scanner fileReader = new Scanner(file)){
            if (!file.canRead() || !file.exists()){
                throw new java.io.FileNotFoundException();
            }

            if(!fileReader.nextLine().equals("P3")) {
                throw new ce326.hw2.UnsupportedFileFormatException();
            }

            super.width = fileReader.nextInt();
            super.height = fileReader.nextInt();
            super.colordepth = fileReader.nextInt();
            super.RGBMatrix = new RGBPixel[height][width];

            for (int i = 0; i < height; i++){
                for (int j = 0; j < width; j++){
                    RGBMatrix[i][j] = new RGBPixel((short) (fileReader.nextInt()), (short) (fileReader.nextInt()), (short) (fileReader.nextInt()));
                }
            }

        }catch (FileNotFoundException ex){
            throw new java.io.FileNotFoundException();

        }catch (UnsupportedFileFormatException ex){
            throw new UnsupportedFileFormatException();
        }
    }

    public PPMImage(RGBImage img){
        super(img);
    }

    public PPMImage(YUVImage img){
        super(img);
    }

    public String toString(){
        StringBuilder line = new StringBuilder();

        line.append("P3"+ "\n");
        line.append(super.width).append(" ");
        line.append(super.height).append("\n");
        line.append(super.colordepth).append("\n");

        for (int i = 0; i < super.height; i++) {
            for (int j = 0; j < super.width; j++) {
                line.append(RGBMatrix[i][j].getRed()).append(" ");
                line.append(RGBMatrix[i][j].getGreen()).append(" ");
                line.append(RGBMatrix[i][j].getBlue()).append(" ");
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
