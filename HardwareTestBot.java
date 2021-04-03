
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
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    public DcMotor ls  = null; //left shooter wheel
    public DcMotor rs = null; //right shooter wheel
    public DcMotor intake = null; //set of two wheels in the front
    public DcMotor compliant = null; //set of three wheels behind the intake
    public Servo arm = null;
    public Servo claw = null;

    public final static double ARM_HOME = 0.96; //starting position for servo arm
    //JUST IN CASE IF CODE GETS MESSED UP: Value must be 0.96
    public final static double ARM_MIN_RANGE = 0.0; //smallest number value allowed for servo position
    //JUST IN CASE IF CODE GETS MESSED UP: Value must be 0.0
    public final static double ARM_MAX_RANGE = 5.0; // largest number value allowed for servo position
    //JUST IN CASE IF CODE GETS MESSED UP: Value must be 5.0

    public final static double CLAW_HOME = 0.0; //starting position for servo arm
    //JUST IN CASE IF CODE GETS MESSED UP: Value must be 0.0
    public final static double CLAW_MIN_RANGE = 0.0; //smallest number value allowed for servo position
    //JUST IN CASE IF CODE GETS MESSED UP: Value must be 0.0
    public final static double CLAW_MAX_RANGE = 5.0; // largest number value allowed for servo position
    //JUST IN CASE IF CODE GETS MESSED UP: Value must be 5.0

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
        leftDriveFront.setDirection(DcMotor.Direction.FORWARD); // Set to FORWARD
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE

        //Shooter Motors
        ls = hwMap.get(DcMotor.class, "ls");
        ls.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE

        rs = hwMap.get(DcMotor.class, "rs");
        rs.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE

        //Intake/Compliant Motors
        intake = hwMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE

        compliant = hwMap.get(DcMotor.class, "compliant");
        compliant.setDirection(DcMotor.Direction.FORWARD);// Set to REVERSE


        // Set all motors to zero power

        //Mecanum Wheels
        leftDriveFront.setPower(0);
        rightDriveFront.setPower(0);
        leftDriveBack.setPower(0);
        rightDriveBack.setPower(0);

        //Shooter Wheels
        ls.setPower(0); //ls stands for Left Shooter
        rs.setPower(0); //rs stands for Right Shooter

        //Intake/Compliant Wheels
        intake.setPower(0);
        compliant.setPower(0);

        //Use RUN_USING_ENCODERS if encoders are installed.
        //Use RUN_WITHOUT_ENCODER if encoders aren't installed.
        leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Shooter Wheels
        ls.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //ls stands for Left Shooter
        rs.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //rs stands for Right Shooter

        //Intake/Compliant Wheels
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        compliant.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Define and initialize ALL installed servos

        //Claw
        claw = hwMap.servo.get("claw"); //set equal to name of the servo motor in the phone
        claw.setPosition(CLAW_HOME); //setPosition actually sets the servo's position and moves it
        //NOTE: make sure that it says CLAW_HOME otherwise it will mess up the program

        //Arm
        arm = hwMap.servo.get("arm"); //set equal to name of the servo motor in the phone
        arm.setPosition(ARM_HOME); //setPosition actually sets the servo's position and moves it
        //NOTE: make sure that it says ARM_HOME otherwise it will mess up the program

    }
}