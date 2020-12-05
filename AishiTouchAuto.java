package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "Touch Sensor Auto")
public class AishiTouchAuto extends LinearOpMode {

    //Hardware
    DcMotor leftDriveFront, rightDriveFront, leftDriveBack, rightDriveBack;

    //Sensor
    DigitalChannel touch;

    //Objects
    ElapsedTime runtime = new ElapsedTime();

    //Variable
    double power = -0.35;

    @Override
    public void runOpMode() {

        //Hardware Mapping
        leftDriveFront = hardwareMap.get(DcMotor.class, "ldf");
        rightDriveFront = hardwareMap.get(DcMotor.class, "rdf");
        leftDriveBack = hardwareMap.get(DcMotor.class, "ldb");
        rightDriveBack = hardwareMap.get(DcMotor.class, "rdb");

        touch = hardwareMap.get(DigitalChannel.class, "touch");

        //Set Direction
        leftDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);
        rightDriveFront.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.FORWARD);

        touch.setMode(DigitalChannel.Mode.INPUT);

        //Wait for Start
        telemetry.addLine("Robot Ready :)");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            //Option 1
//       if(touchSensor.getState()==true){
            if (touch.getState()) {
                power *= -1; // this is the equal to writing power = power * -1
            }
            leftDriveFront.setPower(power);
            leftDriveBack.setPower(power);
            rightDriveFront.setPower(-power);
            rightDriveBack.setPower(power);
        }
    }
}


  /*      while(opModeIsActive()) {

            if (touch.getState()); {
                power *= -0.35;

            }
            //error area begins
            public boolean isTouched(DigitalChannel touch){
                if (touch.getState()) {
                    return true;
                } else {
                    return false;

                }
            }
            public double modPower() {

                double modPower = power*0.85;
                return modPower;

                    //if desired, can make into one line:
                    // return power * 0.85
            //error area ends
        }

    }

}
*/