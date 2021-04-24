
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
     HardwareTestBot robot           = new HardwareTestBot();
     double armPosition = robot.ARM_HOME; //servo safe position
     final double ARM_SPEED = 0.001 ; //sets rate to move servo

     double clawPosition = robot.CLAW_HOME; //servo safe position
     final double CLAW_SPEED = 0.001 ; //sets rate to move servo
    //NOTE: According to my testing 0.001 is the slowest speed. 0.01 is quite fast.

     @Override
     public void runOpMode() {
         double left; //doubles are numbers. They will hold the speed/power for the motors.
         double right;

         /* Initialize the hardware variables.
          * The init() method of the hardware class does all the work here
          */

         robot.init(hardwareMap);

         // Send telemetry message to signify robot waiting;
         telemetry.addData("Hello Driver", "INIT The Program");
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
             float LeftTrigger = gamepad1.left_trigger;
             float RightTrigger = gamepad1.right_trigger;


             //Arm Code
        /*     if (gamepad1.right_trigger) //this moves the arm up
                 armPosition += ARM_SPEED; //add to the servo position

             else if (gamepad1.left_trigger) //this moves the arm down
                 armPosition -= ARM_SPEED; //subtract from the servo position

             //The MIN and MAX range are being incorporated into the program here:
             armPosition = Range.clip(armPosition, robot.ARM_MIN_RANGE, robot.ARM_MAX_RANGE); //make sure the position is valid
             robot.arm.setPosition(armPosition); //this code here ACTUALLY sets the position of the servo so it moves
*/

             //Claw Code
             if (gamepad1.x) //this opens the claw
                 clawPosition += CLAW_SPEED; //add to the servo position

             else if (gamepad1.b) //this closes the claw
                 clawPosition -= CLAW_SPEED; //subtract from the servo position

             //The MIN and MAX range are being incorporated into the program here:
             clawPosition = Range.clip(clawPosition, robot.CLAW_MIN_RANGE, robot.CLAW_MAX_RANGE); //make sure the position is valid
             robot.claw.setPosition(clawPosition); //this code here ACTUALLY sets the position of the servo so it moves

             //Shooter Wheels On
             if (gamepad1.left_stick_button) {
                 robot.s1.setPower(0.3);
                 robot.s2.setPower(-0.3);
             } //Since I set these wheels as REVERSE in the hwmap, these values must be positive so they can move in the REVERSE direction.

             //Shooter Stop
             if (gamepad1.right_stick_button) {

                 robot.s1.setPower(0.0);
                 robot.s2.setPower(0.0);
             }

             //Intake Compliant Start
             if (gamepad1.y) {

                 robot.intake.setPower(0.5);
                 robot.compliant.setPower(-0.4);
             }
             //Intake Compliant Stop

             if (gamepad1.a) {

                 robot.intake.setPower(0.0);
                 robot.compliant.setPower(0.0);
             }

             //Forward
             if (gamepad1.dpad_up) {

                 robot.leftDriveFront.setPower(0.8);
                 robot.leftDriveBack.setPower(-0.8);
                 robot.rightDriveFront.setPower(-0.8);
                 robot.rightDriveBack.setPower(0.8);

             }
             //Backward
             else if (gamepad1.dpad_down) {
                 robot.leftDriveFront.setPower(-0.8);
                 robot.leftDriveBack.setPower(0.8);
                 robot.rightDriveFront.setPower(0.8);
                 robot.rightDriveBack.setPower(-0.8);
             }

             //Strafe Right
             if (gamepad1.dpad_right) {
                //NOTE: all values have to be negative for it to strafe to the right since the robot wheels are inversed, it is different for every robot
                 robot.leftDriveFront.setPower(-0.8);
                 robot.leftDriveBack.setPower(-0.8);
                 robot.rightDriveFront.setPower(-0.8);
                 robot.rightDriveBack.setPower(-0.8);

             }
             //Strafe Left
             if (gamepad1.dpad_left) {
                 //NOTE: all values have to be positive for it to strafe to the left since the robot wheels are inversed, it is different for every robot
                 robot.leftDriveFront.setPower(0.8);
                 robot.leftDriveBack.setPower(0.8);
                 robot.rightDriveFront.setPower(0.8);
                 robot.rightDriveBack.setPower(0.8);

             }

             //Turn Right
             if (gamepad1.right_bumper) {

                 robot.leftDriveFront.setPower(0.8);
                 robot.leftDriveBack.setPower(-0.8);
                 robot.rightDriveFront.setPower(0.8);
                 robot.rightDriveBack.setPower(-0.8);

             }
             //Turn Left
             if (gamepad1.left_bumper) {
                 robot.leftDriveFront.setPower(-0.8);
                 robot.leftDriveBack.setPower(0.8);
                 robot.rightDriveFront.setPower(-0.8);
                 robot.rightDriveBack.setPower(0.8);
             }

        /*Don't need these as it is really hard to control two gamepads
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