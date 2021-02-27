package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Distance Sensor Auto")
public class AishiDistanceAuto extends LinearOpMode {

    DcMotor leftDriveFront, rightDriveFront, leftDriveBack, rightDriveBack;

    DistanceSensor distSensor;

    ElapsedTime runtime = new ElapsedTime();

    double power = 0.85;

    @Override
    public void runOpMode() {

        leftDriveFront  = hardwareMap.get(DcMotor.class, "ldf");
        rightDriveFront = hardwareMap.get(DcMotor.class, "rdf");
        leftDriveBack  = hardwareMap.get(DcMotor.class, "ldb");
        rightDriveBack = hardwareMap.get(DcMotor.class, "rdb");

        distSensor = hardwareMap.get(DistanceSensor.class, "sensor_range");
        leftDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);

        //Optional for TOF (Time of Flight) and Additional Methods

        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)distSensor;

        telemetry.addLine("Robot Ready");
        telemetry.update();

        waitForStart();

        double distMilli = distSensor.getDistance(DistanceUnit.MM);
        double distCenti = distSensor.getDistance(DistanceUnit.CM);
        double distInches = distSensor.getDistance(DistanceUnit.INCH);
        double distMeter = distSensor.getDistance(DistanceUnit.METER);

        telemetry.addData("deviceName",distSensor.getDeviceName());
        telemetry.addData("Millimeters", String.format("%.01f mm",distMilli));

        telemetry.addData("Centimeters", String.format("%.01f cm",distCenti));
        telemetry.addData("Inches", String.format("%.01f in",distInches));
        telemetry.addData("Meters", String.format("%.01f m",distMeter));

        //TOF Information
        telemetry.addData("ID", String.format ("%s", sensorTimeOfFlight.getModelID()));
        telemetry.addData("did time out", Boolean.toString(sensorTimeOfFlight.didTimeoutOccur()));
        telemetry.update();

        // When distInches is greater than 5, robot is going to move back
        while(distInches > 5) {
            leftDriveFront.setPower(-0.25);
            leftDriveBack.setPower(0.25);
            rightDriveFront.setPower(0.25);
            rightDriveBack.setPower(-0.25);
        }

        // Otherwise, set all motors to zero power
        leftDriveFront.setPower(0);
        rightDriveFront.setPower(0);
        leftDriveBack.setPower(0);
        rightDriveBack.setPower(0);

    }

}
