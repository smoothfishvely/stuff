package org.firstinspires.ftc.teamcode.catscan.testers;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
@TeleOp
public class ServoTester extends LinearOpMode {
    public static double servoPos = 0.0;
    public static String servoName = "";
    @Override
    public void runOpMode(){
        waitForStart();
        Servo servo = hardwareMap.get(Servo.class, servoName);
        while(opModeIsActive()) {
            servo.setPosition(servoPos);
        }
    }
}
