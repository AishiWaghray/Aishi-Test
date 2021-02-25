package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Encoder: Auto")
public class EncoderAuto extends LinearOpMode {

    //Declare OpMode Members.
    HardwareTestBot robot   = new HardwareTestBot();

    static final double MOTOR_TICK_COUNTS = 537.6;
    @Override
    public void runOpMode(){

        //Initalize the drive system vars.
        // The init() method of hardware class does all the work here
        robot.init(hardwareMap);

   /* //Send telemetry msg to signify robot waiting
    telemetry.addData("Status","Resetting Encoders");
    telemetry.update();
*/
        //Wait for PLAY to be pressed
        waitForStart();

        //drive forward 18 inches
        //reset the encoders
        robot.leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //how many turns do I need the wheels to go 18 inches
        //the distance you drive with one turn of the wheel is the circumference of the wheel
        double circumference = 3.14*3.75; //in //pi * diameter
        double rotationsNeeded = 44/circumference; //44 instead of 18? Maybe supposed
        //to get the diameter when doing the calculations
        //between number and circumference?
        int encoderDrivingTarget = (int)(rotationsNeeded*537.6); //I don't need to look at the existing
        //encoder counts because I just reset the encoder counts above to 0
        //set the target positions
        robot.leftDriveFront.setTargetPosition(encoderDrivingTarget);
        robot.leftDriveBack.setTargetPosition(encoderDrivingTarget);
        robot.rightDriveFront.setTargetPosition(encoderDrivingTarget);
        robot.rightDriveBack.setTargetPosition(encoderDrivingTarget);

        //set the power desired for the motors
        robot.leftDriveFront.setPower(0.1);
        robot.leftDriveBack.setPower(-0.1);
        robot.rightDriveFront.setPower(-0.1);
        robot.rightDriveBack.setPower(0.1);

        //set the motors to RUN_TO_POSITION
        robot.leftDriveFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftDriveBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightDriveFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightDriveBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.leftDriveFront.isBusy()|| robot.leftDriveBack.isBusy() || robot.rightDriveFront.isBusy() || robot.rightDriveBack.isBusy()) {
            //essentially do nothing while you wait for the robot to finish driving to the position
            telemetry.addData("Path", "Driving 18 inches");
            telemetry.update();
        }

        //Stop all motion now that we have left the loop and the motors have found the destination
        robot.leftDriveFront.setPower(0);
        robot.leftDriveBack.setPower(0);
        robot.rightDriveFront.setPower(0);
        robot.rightDriveBack.setPower(0);

        telemetry.addData("Path","Complete");
        telemetry.update();
    }

}