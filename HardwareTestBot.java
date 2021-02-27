
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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


//*
//This is NOT an opmode.
//This is a copy and now edited version of Hardware Push Bot
//This class can be used to define all the specific hardware for a single robot.
//In this case that robot is a Pushbot.
//See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
//This hardware class assumes the following device names have been configured on the robot:
//Note:  All names are lower case and some have single spaces between words.
//Motor channel:  Left  drive motor:        "left_drive"
// Motor channel:  Right drive motor:        "right_drive"
//Motor channel:  Manipulator drive motor:  "left_arm"
// Servo channel:  Servo to open left claw:  "left_hand"
//Servo channel:  Servo to open right claw: "right_hand"
//@Disabled
public class HardwareTestBot
{
    /* Public OpMode members. */
    public DcMotor leftDriveFront   = null;
    public DcMotor rightDriveFront  = null;
    public DcMotor leftDriveBack   = null;
    public DcMotor rightDriveBack  = null;
    public DcMotor ls  = null;
    public DcMotor rs = null;
    public Servo arm = null;
    public Servo claw = null;
    //public DcMotor  leftArm     = null;
    //public Servo    leftClaw    = null;
    //public Servo    rightClaw   = null;

    public final static double ARM_HOME = 0; //starting position for servo arm
    public final static double ARM_MIN_RANGE = 0; //smallest number value allowed for servo position
    public final static double ARM_MAX_RANGE = 10 ; // largest number value allowed for servo position

    public final static double CLAW_HOME = 0.8; //starting position for servo arm
    public final static double CLAW_MIN_RANGE = 0.1; //smallest number value allowed for servo position
    public final static double CLAW_MAX_RANGE = 5; // largest number value allowed for servo position


    /* public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
**/
    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();



    /* Constructor */
    public HardwareTestBot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDriveFront  = hwMap.get(DcMotor.class, "ldf");
        rightDriveFront = hwMap.get(DcMotor.class, "rdf");
        leftDriveBack  = hwMap.get(DcMotor.class, "ldb");
        rightDriveBack = hwMap.get(DcMotor.class, "rdb");
        leftDriveFront.setDirection(DcMotor.Direction.FORWARD); // Set to FORWARD if using AndyMark motors
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE if using AndyMark motors

        //Shooter Motors
        ls = hwMap.get(DcMotor.class, "ls");
        ls.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE if using AndyMark motors

        rs = hwMap.get(DcMotor.class, "rs");
        rs.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE if using AndyMark motors



        // Set all motors to zero power
        //wheels
        leftDriveFront.setPower(0);
        rightDriveFront.setPower(0);
        leftDriveBack.setPower(0);
        rightDriveBack.setPower(0);

        //shooters
        ls.setPower(0);
        rs.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //shooter
        ls.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rs.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos

        //claw init
        claw = hwMap.servo.get("claw"); //set equal to name of the servo motor in the phone
        claw.setPosition(CLAW_HOME); //setPosition actually sets the servo's position and moves it
        //ARM_HOME sets at 0. ARM_MIN_RANGE sets at 0. ARM_MAX RANGE SETS AT 1.0

        //arm init
        arm = hwMap.servo.get("arm"); //set equal to name of the servo motor in the phone
        arm.setPosition(ARM_HOME); //setPosition actually sets the servo's position and moves it
        //ARM_HOME sets at 0. ARM_MIN_RANGE sets at 0. ARM_MAX RANGE SETS AT 1.0

        //leftClaw  = hwMap.get(Servo.class, "left_hand");
        //rightClaw = hwMap.get(Servo.class, "right_hand");
        //leftClaw.setPosition(MID_SERVO);
        //rightClaw.setPosition(MID_SERVO);
    }
}