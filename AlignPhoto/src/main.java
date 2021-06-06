import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        int[] yCorners = new int[4];
        int[] xCorners = new int[4];
        xCorners[0] = 87;
        xCorners[1] = 509;
        xCorners[2] = 93;
        xCorners[3] = 511;

        yCorners[0] = 81;
        yCorners[1] = 69;
        yCorners[2] = 700;
        yCorners[3] = 695;

        BufferedImage img          = ImageIO.read(new File("src/document.jpg"));
        BufferedImage alignedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < (alignedImage.getWidth()); i++) {
            for (int j = 0; j < (alignedImage.getHeight()); j++) {
                int[] points = getEquivalentPoint(xCorners, yCorners, alignedImage.getHeight(), alignedImage.getWidth(), i,j);
                System.out.println(points[0] + ":" + points[1]);
                alignedImage.setRGB(i, j, img.getRGB(points[0], points[1]));
            }
        }
        ImageIO.write(alignedImage, "png", new File("src/saved.png"));


    }

    public static int[] getEquivalentPoint(int[] xCorners, int[] yCorners, int newHeight, int newWidth, int x, int y) {
        int x1           = xCorners[0];
        int x2           = xCorners[1];
        int x3           = xCorners[2];
        int x4           = xCorners[3];
        int y1           = yCorners[0];
        int y2           = yCorners[1];
        int y3           = yCorners[2];
        int y4           = yCorners[3];

        float xRatio = ((float)(x + 1)/(float)newWidth);
        float yRatio = ((float)(y + 1)/(float)newHeight);

        int deltaX = (x2 - x1) - (x4 - x3);
        int deltaY = (y3 - y1) - (y4 - y2);

        int offSetX = (int) ((yRatio * (x2 - x1)) + x1);
        int scaleAdditionX = (int) (deltaX * yRatio);
        int returnX = offSetX + scaleAdditionX + (x2 - x1);
        int offSetY = (int) ((xRatio * (y3 - y1)) + y1);
        int scaleAdditionY = (int) (deltaY * xRatio);
        int returnY = offSetY + scaleAdditionY + (y3 - y1);

        int[] returnArr = new int[2];
        returnArr[0] = returnX;
        returnArr[1] = returnY;

        return returnArr;
    };
}
