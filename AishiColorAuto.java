package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Color Sensor Auto")
public class AishiColorAuto extends LinearOpMode {

    DcMotor leftDriveFront, rightDriveFront, leftDriveBack, rightDriveBack;
    ColorSensor colorSensor;

    ElapsedTime runtime = new ElapsedTime();

    double power = 0.35;

    @Override
    public void runOpMode() {

        leftDriveFront  = hardwareMap.get(DcMotor.class, "ldf");
        rightDriveFront = hardwareMap.get(DcMotor.class, "rdf");
        leftDriveBack  = hardwareMap.get(DcMotor.class, "ldb");
        rightDriveBack = hardwareMap.get(DcMotor.class, "rdb");

        colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");

        leftDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);

        colorSensor.enableLed(true);

        telemetry.addLine("Robot Ready");
        telemetry.update();
        waitForStart();

        telemetry.addData("Alpha",colorSensor.alpha());
        telemetry.addData("Red",colorSensor.red());
        telemetry.addData("Blue",colorSensor.blue());
        telemetry.addData("Green",colorSensor.green());

        //When colorSensor.red is less than 20, robot is going to move forward
        while(colorSensor.red() < 20) {
            leftDriveFront.setPower(power);
            leftDriveBack.setPower(-power);
            rightDriveFront.setPower(-power);
            rightDriveBack.setPower(power);
        }

        // Otherwise, set all motors to zero power
        leftDriveFront.setPower(0);
        rightDriveFront.setPower(0);
        leftDriveBack.setPower(0);
        rightDriveBack.setPower(0);


    }


}
