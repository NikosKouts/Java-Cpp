package ce326.hw2;


public class RGBImage implements Image {
    int width = 0, height = 0, colordepth = 0;
    static int MAX_COLORDEPTH = 255;
    RGBPixel [][]RGBMatrix;

    public RGBImage(){
    }

    public RGBImage(int width, int height, int colordepth){
        this.RGBMatrix = new RGBPixel[height][width];
        this.width = width;
        this.height = height;
        this.colordepth = colordepth;
    }

    public RGBImage(RGBImage copyImg){
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.colordepth = copyImg.colordepth;
        this.RGBMatrix = new RGBPixel[height][width];

        for (int i = 0; i < copyImg.height; i++){
            for (int j = 0; j < copyImg.width; j++)
                RGBMatrix[i][j] = new RGBPixel(copyImg.RGBMatrix[i][j]);
        }
    }

    public RGBImage(YUVImage YUVImg){
        this.height = YUVImg.height;
        this.width = YUVImg.width;

        this.RGBMatrix = new RGBPixel[this.height][this.width];

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
                RGBMatrix[i][j] = new RGBPixel(YUVImg.YUVMatrix[i][j]);
            }
        }
    }

    int getWidth(){
        return width;
    }

    int getHeight(){
        return height;
    }

    int getColorDepth(){
        return colordepth;
    }

    RGBPixel getPixel(int row, int col){
        return RGBMatrix[row][col];
    }

    void setPixel(int row, int col,  RGBPixel pixel){
        RGBMatrix[row][col] = pixel;
    }

    public void grayscale(){
        short gray = 0;

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                gray = (short) (RGBMatrix[i][j].getRed() * 0.3 + RGBMatrix[i][j].getGreen() * 0.59 + RGBMatrix[i][j].getBlue() * 0.11);
                RGBMatrix[i][j].setRed(gray);
                RGBMatrix[i][j].setGreen(gray);
                RGBMatrix[i][j].setBlue(gray);
            }
        }
    }

    public void doublesize() {
        RGBPixel[][] tempMatrix = new RGBPixel[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++)
                tempMatrix[i][j] = new RGBPixel(RGBMatrix[i][j]);
        }

        this.RGBMatrix = new RGBPixel[2 * this.height][2 * this.width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.RGBMatrix[2 * i][2 * j] = new RGBPixel(tempMatrix[i][j]);
                this.RGBMatrix[2 * i + 1][2 * j] = new RGBPixel(tempMatrix[i][j]);
                this.RGBMatrix[2 * i][2 * j + 1] = new RGBPixel(tempMatrix[i][j]);
                this.RGBMatrix[2 * i + 1][2 * j + 1] = new RGBPixel(tempMatrix[i][j]);
            }
        }
        this.width = 2 * this.width;
        this.height = 2 * this.height;
    }

    public void halfsize() {
        RGBPixel[][] tempMatrix = new RGBPixel[this.height][this.width];
        short red, green, blue;
        
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++)
                tempMatrix[i][j] = new RGBPixel(RGBMatrix[i][j]);
        }

        this.RGBMatrix = new RGBPixel[this.height / 2][this.width / 2];

        for (int i = 0; i < this.height / 2; i++) {
            for (int j = 0; j < this.width / 2; j++) {
                red = (short) ((tempMatrix[2*i][2*j].getRed() + tempMatrix[2*i + 1][2*j].getRed() + tempMatrix[2*i][2*j + 1].getRed() + tempMatrix[2*i + 1][2*j + 1].getRed())/ 4);
                green = (short) ((tempMatrix[2*i][2*j].getGreen() + tempMatrix[2*i + 1][2*j].getGreen() + tempMatrix[2*i][2*j + 1].getGreen() + tempMatrix[2*i + 1][2*j + 1].getGreen())/ 4);
                blue = (short) ((tempMatrix[2*i][2*j].getBlue() + tempMatrix[2*i + 1][2*j].getBlue() + tempMatrix[2*i][2*j + 1].getBlue() + tempMatrix[2*i + 1][2*j + 1].getBlue())/ 4);
                RGBMatrix[i][j] = new RGBPixel(red, green, blue);
            }
        }

        this.width = this.width / 2;
        this.height = this.height / 2;
    }

    public void rotateClockwise() {
        RGBPixel tMatrix[][];
        int tempHeight = 0;

        tMatrix = new RGBPixel[this.height][this.width];
        for(int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                tMatrix[i][j] = new RGBPixel(RGBMatrix[i][j]);
            }
        }

        this.RGBMatrix = new RGBPixel[this.width][this.height];

        for(int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.RGBMatrix[j][this.height - i - 1] = new RGBPixel(tMatrix[i][j]);
            }
        }

        tempHeight = this.height;
        this.height = this.width;
        this.width = tempHeight;
    }
}
