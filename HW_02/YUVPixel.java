package ce326.hw2;

public class YUVPixel {
    int pixel = 0;

    public YUVPixel(short Y, short U, short V){
        pixel = pixel | V;
        pixel = pixel | (U << 8);
        pixel = pixel | (Y << 16);
    }

    public YUVPixel(YUVPixel pixel){
        this.pixel = pixel.pixel;
    }

    public YUVPixel(RGBPixel pixel){
        int Y, U, V;

        Y = ((  66 * pixel.getRed() + 129 * pixel.getGreen() +  25 * pixel.getBlue() + 128) >> 8) +  16;
        U = (( -38 * pixel.getRed() -  74 * pixel.getGreen() + 112 * pixel.getBlue() + 128) >> 8) + 128;
        V = (( 112 * pixel.getRed() -  94 * pixel.getGreen() -  18 * pixel.getBlue() + 128) >> 8) + 128;

        this.pixel = this.pixel | V;
        this.pixel = this.pixel | (U << 8);
        this.pixel = this.pixel | (Y << 16);
    }

    short getY(){
        return (short) (pixel >> 16);
    }

    short getU(){
        return (short) ((pixel & 0x00ff00) >> 8);
    }

    short getV(){
        return (short) (pixel & 0x0000ff);
    }

    void setY(short Y) {
        pixel = pixel & 0x00ffff;
        pixel =  pixel | (Y << 16);
    }

    void setU(short U) {
        pixel = pixel & 0xff00ff;
        pixel =  pixel | (U << 8);
    }

    void setV(short V) {
        pixel = pixel & 0xffff00;
        pixel =  pixel | V;
    }
}
