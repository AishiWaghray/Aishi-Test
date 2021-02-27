

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

//@Disabled
@TeleOp(name="Robot: Teleop", group="Testbot")
public class TeleOpBot extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTestBot robot = new HardwareTestBot();   // Use a Pushbot's hardware
    //double armPosition = robot.ARM_HOME; //servo safe position
    ///final double ARM_SPEED = 0.1 ; //sets rate to move servo
    //0.01 slowest...
    double          armPosition      = 0.3;                       // Servo mid position
    double          clawPosition      = 0;                       // Servo mid position
    final double    ARM_SPEED      = 0.3 ;                  // sets rate to move servo
    final double    CLAW_SPEED      = 0.8 ;                   // sets rate to move servo
    // sets rate to move servo

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


            if (gamepad1.right_bumper) {

                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(0.8);

            }
            //Backward
            else if (gamepad1.left_bumper) {
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
            if (gamepad1.dpad_right) {

                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(0.8);
                robot.rightDriveBack.setPower(-0.8);

            }
            //Turn Left
            if (gamepad1.dpad_left) {
                robot.leftDriveFront.setPower(-0.8);
                robot.leftDriveBack.setPower(0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(0.8);
            }

            //Arm Code
            if (gamepad1.right_stick_button) // move attachment up
                armPosition += ARM_SPEED; //add to the servo position so it moves
            else if (gamepad1.left_stick_button) //move attachment down
                armPosition -= ARM_SPEED; //subtract from the servo position so it moves the other direction

            //Move both servos to new position
            armPosition = Range.clip(armPosition, robot.ARM_MIN_RANGE, robot.ARM_MAX_RANGE); //make sure the position is valid
            robot.arm.setPosition(armPosition); //this code here ACTUALLY sets the position of the servo so it moves


            //Claw Code
            if (gamepad1.y) //open claw
                clawPosition += CLAW_SPEED; //add to the servo position so it moves

            else if (gamepad1.a) //close claw
                clawPosition -= CLAW_SPEED; //subtract from the servo position so it moves the other direction

            //Move both servos to new position
            clawPosition = Range.clip(clawPosition, robot.ARM_MIN_RANGE, robot.ARM_MAX_RANGE); //make sure the position is valid
            robot.claw.setPosition(clawPosition); //this code here ACTUALLY sets the position of the servo so it moves



            //Send telemetry message to signify robot running;
            telemetry.addData("arm", "%.2f", armPosition);
            telemetry.addData("claw", "%.2f", clawPosition);
            // VERY IMPORTANT CODE, shows the values on the phone of the servo
            telemetry.update();
        }
    }
}
