package org.firstinspires.ftc.teamcode;//Telling you where the code is located

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp //@Autonomous
//@Disabled
public class Hello_World extends OpMode
{
    //This is called when the driver press INIT
    @Override
    public void init() { //this sends to the driver station
        telemetry.addData("Hello", "World");
    }

    //This is called repeatedly while OpMode is playing
    @Override
    public void loop() {
        //intentionally left blank
    }
}