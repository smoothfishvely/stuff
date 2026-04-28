package org.firstinspires.ftc.teamcode.catscan.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PurpleOrGreen extends OpenCvPipeline {

    final int cameraWidth = 1184;
    final int cameraHeight = 656;
    static boolean isOnRight = false;
    static boolean isOnLeft = false;
    static boolean isOnMiddle = false;
    double purplePixelCountL = 0, greenPixelCountL = 0, purplePixelCountR = 0, greenPixelCountR = 0;    Mat YCbCr = new Mat();
    Mat maskTest = new Mat();
    Mat leftSide = new Mat();
    Mat rightSide = new Mat();
    Mat middleSide = new Mat();
    Mat output = new Mat();
    Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

    static double leftavgfin;
    static double rightavgfin;
    static double middleavgfin;
    Rect leftRect = new Rect(1, cameraHeight / 3, (cameraWidth / 3) - 1, (int) (cameraHeight) / 5);
    Rect middleRect = new Rect(cameraWidth / 3, cameraHeight / 3, (cameraWidth / 3) - 1, (int) (cameraHeight) / 5);
    Rect rightRect = new Rect((cameraWidth / 3 * 2) + 1, cameraHeight / 3, (cameraWidth / 3) - 1, (int) (cameraHeight) / 5);

    //I just picked some random values with the colorwheel for a lower and upper bound for green
    //but feel free to change these if its not picking up the ball - Keir
    Scalar lowerGreen = new Scalar(60, 70, 70);
    Scalar upperGreen = new Scalar(100, 255, 255);

    Scalar lowerPurple = new Scalar(130, 50, 50);
    Scalar upperPurple = new Scalar(180, 255, 255);


    @Override
    public Mat processFrame(Mat input) {
        // Clear Mats
        leftSide.release();
        rightSide.release();
        middleSide.release();
        output.release();


        Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2HSV);

        input.copyTo(output);
        Imgproc.rectangle(output, leftRect, rectColor, 2);
        Imgproc.rectangle(output, rightRect, rectColor, 2);
        Imgproc.rectangle(output, middleRect, rectColor, 2);


        leftSide = YCbCr.submat(leftRect);
        rightSide = YCbCr.submat(rightRect);
        middleSide = YCbCr.submat(middleRect);

        //extracts the channel, since its YCRCB
        //channel 0 is Y (luma) channel 1 is CR (red) channel 2 is CB (blue)

        maskTest = new Mat();

        Core.inRange(leftSide, lowerGreen, upperGreen, maskTest);
        greenPixelCountL = Core.sumElems(maskTest).val[0];

        Core.inRange(leftSide, lowerPurple, upperPurple, maskTest);
        purplePixelCountL = Core.sumElems(maskTest).val[0];

        Core.inRange(rightSide, lowerGreen, upperGreen, maskTest);
        greenPixelCountR = Core.sumElems(maskTest).val[0];

        Core.inRange(rightSide, lowerPurple, upperPurple, maskTest);
        purplePixelCountR = Core.sumElems(maskTest).val[0];


        System.out.println("Green Pixel count of left side" + greenPixelCountL);
        System.out.println("Green Pixel count of right side" + greenPixelCountR);
        System.out.println("Purple Pixel count of left side" + purplePixelCountL);
        System.out.println("Purple Pixel count of right side" + purplePixelCountR);


        if (middleavgfin > leftavgfin && middleavgfin > rightavgfin) {
            isOnMiddle = true;
            isOnRight = false;
            isOnLeft = false;
        } else if (leftavgfin > rightavgfin && leftavgfin > middleavgfin) {
            isOnLeft = true;
            isOnRight = false;
            isOnMiddle = false;
        } else {
            isOnRight = true;
            isOnLeft = false;
            isOnMiddle = false;
        }

        return output;
    }

    public boolean getLatestResultR() {
        return isOnRight;
    }

    public boolean getLatestResultL() {
        return isOnLeft;
    }
    public double getGreenPixels() {
        return greenPixelCountL + greenPixelCountR;
    }

    public double getPurplePixels() {
        return purplePixelCountL + purplePixelCountR;
    }

    public boolean hasPurpleBall() {
        if (purplePixelCountL > 30000) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean hasGreenBall() {
        if (greenPixelCountL > 30000) {
            return true;
        }
        else {
            return false;
        }
    }

    public String debugging() {
        return "\n green left:" + greenPixelCountL + "\n green right: " + greenPixelCountR + "\n green middle: " + null;
    }

    public String debuggingGreen() {
        return "\n green left:" + greenPixelCountL + "\n green right: " + greenPixelCountR + "\n green middle: " + null;
    }

    public String debuggingPurple() {
        return "\n purple left:" + purplePixelCountL + "\n purple right: " + purplePixelCountR + "\n green middle: " + null;
    }

    public boolean getLatestResultM() {
        return isOnMiddle;
    }
}