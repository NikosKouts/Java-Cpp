package ce326.hw2;

public class RGBPixel {
    int pixel = 0;

    public RGBPixel(short red, short green, short blue){
        pixel = pixel | blue;
        pixel = pixel | (green << 8);
        pixel = pixel | (red << 16);

    }

    public RGBPixel(RGBPixel pixel){
        this.pixel = pixel.pixel;
    }

    public RGBPixel(YUVPixel pixel){
        int C, D, E, red, green, blue;

        C = pixel.getY() - 16;
        D = pixel.getU() - 128;
        E = pixel.getV() - 128;

        red = clip(( 298 * C + 409 * E + 128) >> 8);
        green = clip(( 298 * C - 100 * D - 208 * E + 128) >> 8);
        blue = clip(( 298 * C + 516 * D + 128) >> 8);

        setRGB((short) red, (short) green, (short) blue);
    }

    private int clip(int i) {
        if (i > 255)
            i = 255;

        if(i < 0)
            i = 0;

        return (i);
    }

    public short getRed() { return (short) (pixel >> 16); }

    public short getGreen(){
        return (short)((pixel & 0x00ffff) >> 8);
    }

    public short getBlue(){
        return (short)(pixel & 0x0000ff);
    }

    void setRed(short red){
        pixel = pixel & 0x00ffff;
        pixel = pixel | (red << 16);
    }

    void setGreen(short green){
        pixel = pixel & 0xff00ff;
        pixel = pixel | (green << 8);
    }

    void setBlue(short blue){
        pixel = pixel & 0xffff00;
        pixel = pixel | blue;
    }

    int getRGB(){
        return pixel;
    }

    void setRGB(int value){
        value = pixel;
    }

    final void setRGB(short red, short green, short blue){
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public String toString(){
        String RGB;

        RGB = String.format("%d %d %d", getRed(), getGreen(), getBlue());

        return RGB;
    }


}
