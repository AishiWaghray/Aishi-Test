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

@TeleOp(name="Testbot: Teleop", group="Testbot")
//@Disabled
public class TestbotTeleopPOV_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTestBot robot           = new HardwareTestBot();   // Use a Pushbot's hardware
    //double          armPosition      = 0;                       // Servo mid position
    //double          clawPosition      = 0;                       // Servo mid position
    //final double    CLAW_SPEED      = 0.02 ;                  // sets rate to move servo
    //final double    CLAW_SPEED      = 0.02 ;                   // sets rate to move servo

    @Override
    public void runOpMode() {
        double left; //doubles are numbers. They will hold the speed/power for the motors.
        double right;
        double drive;
        double turn;
        double max;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {



            // Run wheels in POV mode/. (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;

            // Combine drive and turn for blended motion.
            left  = drive + turn;
            right = drive - turn;

            // Normalize the values so neither exceed +/- 1.0
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0)
            {
                left /= max;
                right /= max;
            }

            // Output the safe vales to the motor drives.
            double RightStickY = gamepad1.right_stick_y;
            double LeftStickY = gamepad1.left_stick_y;
            boolean RightBumper = gamepad1.right_bumper;
            boolean LeftBumper = gamepad1.left_bumper;
            robot.leftDriveFront.setPower(LeftStickY);
            robot.leftDriveBack.setPower(LeftStickY);
            robot.rightDriveFront.setPower(RightStickY);
            robot.rightDriveBack.setPower(RightStickY);

            //Strafe Right
            if (RightBumper) {

                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(0.8);

            }
            //Strafe Left
            else if (LeftBumper) {
                robot.leftDriveFront.setPower(-0.8);
                robot.leftDriveBack.setPower(0.8);
                robot.rightDriveFront.setPower(0.8);
                robot.rightDriveBack.setPower(-0.8);
            }
            //Up Right
            if (gamepad1.a) {
                robot.leftDriveFront.setPower(0.8);
                robot.leftDriveBack.setPower(0.0);
                robot.rightDriveFront.setPower(0.0);
                robot.rightDriveBack.setPower(0.8);
            }
            //Back Right
            else if (gamepad1.b) {
                robot.leftDriveFront.setPower(-0.8);
                robot.leftDriveBack.setPower(0.0);
                robot.rightDriveFront.setPower(0.0);
                robot.rightDriveBack.setPower(-0.8);
            }
            //Up Left
            else if (gamepad1.x) {
                robot.leftDriveFront.setPower(0.0);
                robot.leftDriveBack.setPower(0.8);
                robot.rightDriveFront.setPower(0.8);
                robot.rightDriveBack.setPower(0.0);
            }
            //Back Left
            else if (gamepad1.y) {
                robot.leftDriveFront.setPower(0.0);
                robot.leftDriveBack.setPower(-0.8);
                robot.rightDriveFront.setPower(-0.8);
                robot.rightDriveBack.setPower(0.0);
            }
            //Move FWD and Backward
            else {
                robot.leftDriveFront.setPower(LeftStickY);
                robot.leftDriveBack.setPower(LeftStickY);
                robot.rightDriveFront.setPower(RightStickY);
                robot.rightDriveBack.setPower(RightStickY);

            }
            telemetry.addData("left", "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
        }        sleep(50);
    }
}