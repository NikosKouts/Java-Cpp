package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PPMImageStacker {
    private final ArrayList<PPMImage> ppmList;
    private final RGBImage stackedImage;

    public PPMImageStacker(java.io.File dir) throws FileNotFoundException, UnsupportedFileFormatException {
        if(!dir.exists()) {
            System.out.println("[ERROR] Directory <"+ dir.getName() + ">" + "does not exist!");
            throw new java.io.FileNotFoundException();
        }
        else if(!dir.isDirectory()) {
            System.out.println("[ERROR]" + dir.getName() + "is not a directory!");
            throw new java.io.FileNotFoundException();
        }


        ppmList = new ArrayList<PPMImage>();
        File[] filePath1 = dir.listFiles();

        for (File filePath : filePath1) {
            ppmList.add(new PPMImage(filePath));
        }

        stackedImage=new RGBImage(ppmList.get(0).getWidth(), ppmList.get(0).getHeight(), ppmList.get(0).getColorDepth());
    }

    public void stack(){
        int red, green, blue;

        for(int i = 0; i < ppmList.get(0).getHeight(); i++) {
            for(int j = 0; j < ppmList.get(0).getWidth(); j++) {
                red = 0;
                green = 0;
                blue = 0;

                for (PPMImage current :ppmList) {
                    red += current.RGBMatrix[i][j].getRed();
                    green += current.RGBMatrix[i][j].getGreen();
                    blue += current.RGBMatrix[i][j].getBlue();
                }
                stackedImage.RGBMatrix[i][j] = new RGBPixel((short) (red/ppmList.size()), (short) (green/ppmList.size()), (short) (blue/ppmList.size()));
            }
        }
    }

    public PPMImage getStackedImage(){
        return (new PPMImage(stackedImage));
   }
}
