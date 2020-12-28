package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//@Disabled
@TeleOp(name="Robot: Teleop", group="Testbot")
public class TeleOpBot extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTestBot robot = new HardwareTestBot();   // Use a Pushbot's hardware
    //double armPosition = robot.ARM_HOME; //servo safe position
    ///final double ARM_SPEED = 0.1 ; //sets rate to move servo
    //0.01 slowest...
    double armPosition = 0;                       // Servo mid position
    final double ARM_SPEED = 0.3;                  // sets rate to move servo

    @Override
    public void runOpMode() {
        double left; //doubles are numbers. They will hold the speed/power for the motors.
        double right;
       /* double drive;
        double turn;
        double max;
*/
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Forward and Backward
            boolean RightBumper = gamepad1.right_bumper;
            boolean LeftBumper = gamepad1.left_bumper;
            //Turning Left and Right
            boolean Dpadl = gamepad1.dpad_left;
            boolean Dpadr = gamepad1.dpad_right;

            //Forward
            if (RightBumper) {

                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(0.8);

            }
            //Backward
            else if (LeftBumper) {
                robot.leftDriveFront.setPower(-0.8);
                robot.leftDriveBack.setPower(0.8);
                robot.rightDriveFront.setPower(0.8);
                robot.rightDriveBack.setPower(-0.8);
            }

            //Strafe Right
            if (gamepad1.b) {

                robot.leftDriveFront.setPower(-0.8);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(-0.8);

            }
            //Strafe Left
            if (gamepad1.x) {
                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(0.8);
                robot.rightDriveFront.setPower(0.8);
                robot.rightDriveBack.setPower(0.8);

            }

            //Turn Right
            if (Dpadr) {

                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(0.8);
                robot.rightDriveBack.setPower(-0.8);

            }
            //Turn Left
            if (Dpadl) {
                robot.leftDriveFront.setPower(-0.8);
                robot.leftDriveBack.setPower(0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(0.8);
            }

            //ArmMotor Up
            if (gamepad1.y);
            {

                robot.armMotor.setPower(0.01);
            }

            //ArmMotor Down
            if (gamepad1.a);
            {

                robot.armMotor.setPower(-0.01);
            }


        }
    }
}