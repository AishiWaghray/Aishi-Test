 /* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
//@Disabled
@TeleOp(name="Testbot2: Teleop", group="Testbot")

public class Testbot2 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTestBot robot           = new HardwareTestBot();   // Use a Pushbot's hardware
    double armPosition = robot.ARM_HOME; //servo safe position
    final double ARM_SPEED = 0.001 ; //sets rate to move servo

    double clawPosition = robot.CLAW_HOME; //servo safe position
    final double CLAW_SPEED = 0.001 ; //sets rate to move servo
    //0.01 slowest...
/*  double          armPosition      = 0.3;                       // Servo mid position
    double          clawPosition      = 0;                       // Servo mid position
    final double    ARM_SPEED      = 0.4 ;                  // sets rate to move servo
    final double    CLAW_SPEED      = 0.8 ;                   // sets rate to move servo
*/
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

            // Output the safe vales to the motor drives.
           // double RightStickY = gamepad1.right_stick_y;
           // double LeftStickY = gamepad1.left_stick_y;

            //Setting Variables for Gamepad1
            double RightStickY = gamepad1.right_stick_y;
            double LeftStickY = gamepad1.left_stick_y;
            boolean RightBumper = gamepad1.right_bumper;
            boolean LeftBumper = gamepad1.left_bumper;
            boolean Dpadl = gamepad1.dpad_left;
            boolean Dpadr = gamepad1.dpad_right;
            robot.leftDriveFront.setPower(LeftStickY);
            robot.leftDriveBack.setPower(LeftStickY);
            robot.rightDriveFront.setPower(RightStickY);
            robot.rightDriveBack.setPower(RightStickY);

            //I DON'T NEED THIS CURRENTLY BECAUSE I AM NOT USING A SECOND GAMEPAD.
            //Setting Variables for Gamepad2
      /*     boolean RightBumper2 = gamepad2.right_bumper
            boolean LeftBumper2 = gamepad2.left_bumper;
            boolean Dpadl2 = gamepad2.dpad_left;
            boolean Dpadr2 = gamepad2.dpad_right; */





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
            clawPosition = Range.clip(clawPosition, robot.CLAW_MIN_RANGE, robot.CLAW_MAX_RANGE); //make sure the position is valid
            robot.claw.setPosition(clawPosition); //this code here ACTUALLY sets the position of the servo so it moves

        //shooter reverse
            if (gamepad1.dpad_down) {

                robot.shooter.setPower(-1.0);
                robot.shooter2.setPower(-0.8);
                robot.shooter3.setPower(-0.6);
            }

         //shooter stop

                if (gamepad1.dpad_up) {

                    robot.shooter.setPower(0.0);
                    robot.shooter2.setPower(0.0);
                    robot.shooter3.setPower(0.0);
                }


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

         /* //When I start using a second gamepad, I will add the diagonals back in.
         //Up Right Diagonal
            if (gamepad1.a) {
                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(0.0);
                robot.rightDriveFront.setPower(0.0);
                robot.rightDriveBack.setPower(0.8);
            }
            //Back Right Diagonal
            else if (gamepad1.b) {
                robot.leftDriveFront.setPower(-0.8);
                robot.leftDriveBack.setPower(0.0);
                robot.rightDriveFront.setPower(0.0);
                robot.rightDriveBack.setPower(-0.8);
            }
            //Up Left Diagonal
            else if (gamepad1.x) {
                robot.leftDriveFront.setPower(0.0);
                robot.leftDriveBack.setPower(0.8);
                robot.rightDriveFront.setPower(0.8);
                robot.rightDriveBack.setPower(0.0);
            }
            //Back Left Diagonal
            else if (gamepad1.y) {
                robot.leftDriveFront.setPower(0.0);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(0.0);
            } */
         //Turns slightly to left or right
          else {
                robot.leftDriveFront.setPower(LeftStickY);
                robot.leftDriveBack.setPower(LeftStickY);
                robot.rightDriveFront.setPower(RightStickY);
                robot.rightDriveBack.setPower(RightStickY);

            }
           //Send telemetry message to signify robot running;
            telemetry.addData("arm", "%.2f", armPosition);
            telemetry.addData("claw", "%.2f", clawPosition);
        // VERY IMPORTANT CODE, shows the values on the phone of the servo
            //telemetry.addData("left", "%.2f", left);
            //telemetry.addData("right", "%.2f", right);
            telemetry.update();

                // Pace this loop so jaw action is reasonable speed.
            }        sleep(50);
    }
}
