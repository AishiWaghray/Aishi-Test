/* Copyright (c) 2019 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Ultimate Goal game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Competition_Auto", group = "Concept")
//@Disabled
public class Competition_Auto extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "Ac3yuuL/////AAABma08SmMGnkfIiHr49vyPpqRm5Np+l4ghb8bOAngQ4SEeUOlK7pxC1ID2EKzHadqRK2Oz7wK4tSDT9LL" +
                    "2MZOndMbsiqLi8DpONaWFvE2wDROONXU7SjDPZqUUVh2309UtBOPmB4CBFfxNqcrOMGhYKs2dXI7Pv6iIsYpLX7" +
                    "uP/Je4bnZ/tjfF1+0bnMI5ltFQD+MxwQ8q/5YFnr7x9POlPO2a8AUdNDG4PCx7fOKhzdN56FsqtkDZ4JoW6vNS5" +
                    "1zUnSa2TAq3LzpRQfxT2wRMbefAu7BlORUbZnQR4DS7xY9lyozNRf5m7Xb3+tcKGETrDW7UCSwpDn2abSjZCIcf" +
                    "VRveoSpziuiouOnmOahGg94O";

    //Creating 3 Variables
    private boolean stackCameraStop = true;
    private int targetZone = 0;
    private float cameraNumber = 0;
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        //setting the motors
        DcMotor leftDriveFront = null;
        DcMotor leftDriveBack = null;
        DcMotor rightDriveFront = null;
        DcMotor rightDriveBack = null;

        leftDriveFront = hardwareMap.dcMotor.get("ldf");
        leftDriveBack = hardwareMap.dcMotor.get("ldb");
        rightDriveFront = hardwareMap.dcMotor.get("rdf");
        rightDriveBack = hardwareMap.dcMotor.get("rdb");

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.7, 1.78);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Scanning Done. Begin the program.");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (!isStopRequested() && (stackCameraStop)) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 0 ) {
                            // empty list.  no objects recognized.
                            telemetry.addData("TFOD", "No items detected.");
                            telemetry.addData("Driving to Target Zone", "A");
                            targetZone = 1;

                            //driving forward
                            leftDriveFront.setPower(0.5);
                            leftDriveBack.setPower(-0.5);
                            rightDriveFront.setPower(0.5);
                            rightDriveBack.setPower (0.5);
                            sleep (1000);

                            //strafing left
                            leftDriveFront.setPower(-0.55);
                            leftDriveBack.setPower(-0.55);
                            rightDriveFront.setPower(0.55);
                            rightDriveBack.setPower (-0.55);
                            sleep(1000);

                            //driving forward
                            leftDriveFront.setPower(0.55);
                            leftDriveBack.setPower(-0.55);
                            rightDriveFront.setPower(0.55);
                            rightDriveBack.setPower (0.55);
                            sleep (1000);

                            //driving forward
                            leftDriveFront.setPower(0.5);
                            leftDriveBack.setPower(-0.5);
                            rightDriveFront.setPower(0.5);
                            rightDriveBack.setPower (0.5);
                            sleep (1000);

                            //stop
                            leftDriveFront.setPower(0);
                            leftDriveBack.setPower(0);
                            rightDriveFront.setPower(0);
                            rightDriveBack.setPower (0);
                            sleep(1000000000);

                        } else {
                            // list is not empty.
                            // step through the list of recognitions and display boundary info.
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                // check label to see which target zone to go after.
                                if (recognition.getLabel().equals("Single")) {
                                    telemetry.addData("Driving to Target Zone", "B");
                                    targetZone = 2;

                                } else if (recognition.getLabel().equals("Quad")) {
                                    telemetry.addData("Driving to Target Zone", "C");
                                    targetZone = 3;

                                    //driving forward
                                    leftDriveFront.setPower(0.5);
                                    leftDriveBack.setPower(-0.5);
                                    rightDriveFront.setPower(0.5);
                                    rightDriveBack.setPower (0.5);
                                    sleep (1000);

                                    //strafing left
                                    leftDriveFront.setPower(-0.55);
                                    leftDriveBack.setPower(-0.55);
                                    rightDriveFront.setPower(0.55);
                                    rightDriveBack.setPower (-0.55);
                                    sleep(1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.55);
                                    leftDriveBack.setPower(-0.55);
                                    rightDriveFront.setPower(0.55);
                                    rightDriveBack.setPower (0.55);
                                    sleep (1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.55);
                                    leftDriveBack.setPower(-0.55);
                                    rightDriveFront.setPower(0.55);
                                    rightDriveBack.setPower (0.55);
                                    sleep (1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.55);
                                    leftDriveBack.setPower(-0.55);
                                    rightDriveFront.setPower(0.55);
                                    rightDriveBack.setPower (0.55);
                                    sleep (1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.5);
                                    leftDriveBack.setPower(-0.5);
                                    rightDriveFront.setPower(0.5);
                                    rightDriveBack.setPower (0.5);
                                    sleep (1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.5);
                                    leftDriveBack.setPower(-0.5);
                                    rightDriveFront.setPower(0.5);
                                    rightDriveBack.setPower (0.5);
                                    sleep (1000);

                                    //strafing left
                                    leftDriveFront.setPower(-0.55);
                                    leftDriveBack.setPower(-0.55);
                                    rightDriveFront.setPower(0.55);
                                    rightDriveBack.setPower (-0.55);
                                    sleep(1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.55);
                                    leftDriveBack.setPower(-0.55);
                                    rightDriveFront.setPower(0.55);
                                    rightDriveBack.setPower (0.55);
                                    sleep (1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.5);
                                    leftDriveBack.setPower(-0.5);
                                    rightDriveFront.setPower(0.5);
                                    rightDriveBack.setPower (0.5);
                                    sleep (1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.5);
                                    leftDriveBack.setPower(-0.5);
                                    rightDriveFront.setPower(0.5);
                                    rightDriveBack.setPower (0.5);
                                    sleep (1000);

                                    //driving forward
                                    leftDriveFront.setPower(0.5);
                                    leftDriveBack.setPower(-0.5);
                                    rightDriveFront.setPower(0.5);
                                    rightDriveBack.setPower (0.5);
                                    sleep (1000);

                                    //driving backward
                                    leftDriveFront.setPower(-0.5);
                                    leftDriveBack.setPower(0.5);
                                    rightDriveFront.setPower(-0.5);
                                    rightDriveBack.setPower (-0.5);
                                    sleep (1000);

                                    //stop
                                    leftDriveFront.setPower(0);
                                    leftDriveBack.setPower(0);
                                    rightDriveFront.setPower(0);
                                    rightDriveBack.setPower (0);
                                    sleep(1000000000);
                                    sleep (15000); //sleeping for 15 seconds

                                    //driving backward
                                    leftDriveFront.setPower(-0.6);
                                    leftDriveBack.setPower(0.6);
                                    rightDriveFront.setPower(-0.6);
                                    rightDriveBack.setPower (-0.6);
                                    sleep (1000);

                                    //stop
                                    leftDriveFront.setPower(0);
                                    leftDriveBack.setPower(0);
                                    rightDriveFront.setPower(0);
                                    rightDriveBack.setPower (0);
                                    sleep(1000000000);

                                } else {
                                    telemetry.addData("Target Zone", "UNKNOWN");
                                }
                            }
                        }

                        telemetry.update();
                    }
                }

                //this will make the camera number variable go up by one
                //each time it goes through the while loop
                cameraNumber += 1;

                //stopping the while loop from running indefinitely
                if (cameraNumber == 200) {
                    //the reason there are two equals is since one equal sign means you are
                    //setting the variable to something while two equal signs signifies that you're asking
                    //if it is set to something

                    stackCameraStop = false;
                    cameraNumber = 0;
                }

                }
            }
    //    }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minResultConfidence = 0.8f;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
