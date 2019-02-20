package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.hardware.DigitalChannel;
// ta ma de ni mama ing j ing
import java.sql.Driver;

/**
 * Created by Sean Burnham on 11/26/2018.
 */

@Autonomous(name = "PR", group = "Autonomous Mecanum")
public class pitsideright extends LinearOpMode {

    //make object of mecanum hardware class
    bestteamcode4point3 robot = new bestteamcode4point3();
    //declare an object of the ElapsedTime class to allow you to calculate how long you've been driving
    private ElapsedTime runtime = new ElapsedTime();

    //Variables you will need to calculate the circumference of the wheel and how long it takes to
    //spin the wheel once. 'final' means that it cannot be changed anywhere else in the program,
    //which denotes that this variable is permanent (you can't change the hardware dimensions, so the
    //program shouldn't either)
    static final double COUNTS_PER_MOTOR_REV = 1680;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double circumference = 2 * 3.1415 * 9.5;
    static final double one_degree = circumference / 360;
    static final double DRIVE_SPEED = 0.8;
    static final double TURN_SPEED = 1;

    @Override
    public void runOpMode() {
        //start the init method in the hardware class
        robot.init(hardwareMap);

        //send some telemetry messages to tell you that it's running
        telemetry.addData("IT'S RUNNING WTF", "autonomous");
        telemetry.update();

        //set the grabbers to be closed at start so we can put the glyph in it
        //robot.servo1.setPosition(null);

        //tell the encoders to reset for a hot sec
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        GoldAlignDetector detector;

        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 300; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        // The default value is 100; here we change it to 300 to have a tolerance
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        /*detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //*/

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA; // Can also be PERFECT_AREA
        detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Program for the robot to navigate! YAY!

        // drive to thing placement area, then turn around and park over the barrier.
        // PUT THE STUFF HERE

        //dropDown();
        encoderShift(1, 4, 10, null);
        // encoderDrive(1, .001, .001, 10, null);
        encoderShift(1, -4, 10, null);
        yeetIt(2000);
        doTheRoar(2728);
        encoderDrive(1, 15, 15, 10, null);
        encoderDrive(1, -15, -15, 10, null);

        /*int i = 0;
        double[] angles = {32.25, 38.25, 38.25};
        while(i != 0){
            int yeetus = (int)((angles[i] / 360.0)  * 19 / WHEEL_DIAMETER_INCHES * COUNTS_PER_MOTOR_REV);

            encoderDrive(1, yeetus, -yeetus, 50, null);

            if(detector.getAligned()) {
                encoderDrive(1, -30, -30, 50, null);
                encoderDrive(1, 50, 50, 50, null);
                break;
            }
            i++;
        }*/

        doTheRoar(-2728);

        // encoderShift(1, 35, 50, null);
        // encoderDrive(1, -56, 56, 50, null);
        // encoderDrive(1, 10, 10, 50, null);
        // encoderDrive(1, -70, -70, 50, null);
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    void runWithEncoder(int frontleftCounters, int frontleftDirection, int frontrightCounters, int frontrightDirection,
                        int backleftCounters, int backleftDirection, int backrightCounters, int backrightDirection) {
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.frontRightDrive.setTargetPosition(frontrightCounters);
        robot.frontLeftDrive.setTargetPosition(frontleftCounters);
        robot.backRightDrive.setTargetPosition(backrightCounters);
        robot.backLeftDrive.setTargetPosition(backleftCounters);

        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontRightDrive.setPower(frontrightDirection);
        robot.frontLeftDrive.setPower(frontleftDirection);
        robot.backRightDrive.setPower(backrightDirection);
        robot.backLeftDrive.setPower(backleftDirection);

        while(robot.frontRightDrive.isBusy() && robot.frontLeftDrive.isBusy()){
            telemetry.addData("FRONT RIGHT:", robot.frontRightDrive.getCurrentPosition());
            telemetry.addData("FRONT LEFT:", robot.frontLeftDrive.getCurrentPosition());
            telemetry.addData("BACK RIGHT:", robot.backRightDrive.getCurrentPosition());
            telemetry.addData("BACK LEFT:", robot.backLeftDrive.getCurrentPosition());

            telemetry.update();
        }

        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);

        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    void move_to_right(double inches){
        int displacement = (int) (inches * COUNTS_PER_INCH);
        runWithEncoder(-displacement, 1, -displacement, 1, displacement, 1, displacement, 1);
    }
    void doTheRoar(double counts) {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int) (counts);
            newFrontRightTarget = robot.frontRightDrive.getCurrentPosition() + (int) (counts);
            newBackLeftTarget = robot.backLeftDrive.getCurrentPosition() + (int) (counts);
            newBackRightTarget = robot.backRightDrive.getCurrentPosition() + (int) (counts);

            robot.frontLeftDrive.setTargetPosition(newFrontLeftTarget);
            robot.frontRightDrive.setTargetPosition(newFrontRightTarget);
            robot.backLeftDrive.setTargetPosition(newBackLeftTarget);
            robot.backRightDrive.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            double power = Math.abs(1);

            robot.frontLeftDrive.setPower(power);
            robot.frontRightDrive.setPower(power);
            robot.backLeftDrive.setPower(power);
            robot.backRightDrive.setPower(power);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < 10) &&
                    (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy())) {
                int whereFrontRightThinks = robot.frontLeftDrive.getCurrentPosition();
                int whereFrontLeftThinks = robot.frontRightDrive.getCurrentPosition();
                int whereBackRightThinks = robot.backLeftDrive.getCurrentPosition();
                int whereBackLeftThinks = robot.backRightDrive.getCurrentPosition();

                telemetry.addData("front right position: ", whereFrontRightThinks);
                telemetry.addData("front left position: ", whereFrontLeftThinks);
                telemetry.addData("back right position: ", whereBackRightThinks);
                telemetry.addData("back left position: ", whereBackLeftThinks);

                telemetry.update();
            }

            // Stop all motion;
            robot.frontLeftDrive.setPower(0);
            robot.frontRightDrive.setPower(0);
            robot.backLeftDrive.setPower(0);
            robot.backRightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void dropDown() {
        robot.bigLift.setTargetPosition(robot.bigLift.getCurrentPosition() - 36500);
        robot.bigLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.bigLift.setPower(1);

        while(robot.bigLift.isBusy()) {
            telemetry.addData("BIG LIFT ENCODER: ", robot.bigLift.getCurrentPosition());
            telemetry.update();
        }

        robot.bigLift.setPower(0);
        robot.bigLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        move_to_right(10);
        encoderDrive(1, 10, 10, 50, null);
        move_to_right(-10);
    }
    public void yeetIt(int skrrt) {
        robot.loganPaulerLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int newLPLTarget = (robot.loganPaulerLift.getCurrentPosition() + skrrt);

        robot.loganPaulerLift.setTargetPosition(newLPLTarget);
        robot.loganPaulerLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.loganPaulerLift.setPower(0.4);

        while (robot.loganPaulerLift.isBusy()) {
            telemetry.addData("SCOOPAROONI ENCODER: ", robot.loganPaulerLift.getCurrentPosition());
            telemetry.update();
        }

        robot.loganPaulerLift.setPower(0);
        robot.loganPaulerLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void encoderDrive(double speed,
                             double lInches,
                             double rInches,
                             double timeoutS, String msg) {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int) (-lInches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRightDrive.getCurrentPosition() + (int) (rInches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeftDrive.getCurrentPosition() + (int) (-lInches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRightDrive.getCurrentPosition() + (int) (rInches * COUNTS_PER_INCH);

            robot.frontLeftDrive.setTargetPosition(newFrontLeftTarget);
            robot.frontRightDrive.setTargetPosition(newFrontRightTarget);
            robot.backLeftDrive.setTargetPosition(newBackLeftTarget);
            robot.backRightDrive.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            double power = Math.abs(speed);

            robot.frontLeftDrive.setPower(power);
            robot.frontRightDrive.setPower(power);
            robot.backLeftDrive.setPower(power);
            robot.backRightDrive.setPower(power);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy())) {
                int whereFrontRightThinks = robot.frontLeftDrive.getCurrentPosition();
                int whereFrontLeftThinks = robot.frontRightDrive.getCurrentPosition();
                int whereBackRightThinks = robot.backLeftDrive.getCurrentPosition();
                int whereBackLeftThinks = robot.backRightDrive.getCurrentPosition();

                telemetry.addData("front right position: ", whereFrontRightThinks);
                telemetry.addData("front left position: ", whereFrontLeftThinks);
                telemetry.addData("back right position: ", whereBackRightThinks);
                telemetry.addData("back left position: ", whereBackLeftThinks);

                telemetry.update();
            }

            // Stop all motion;
            robot.frontLeftDrive.setPower(0);
            robot.frontRightDrive.setPower(0);
            robot.backLeftDrive.setPower(0);
            robot.backRightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void encoderShift(double speed,
                             double inches,
                             double timeoutS, String msg) {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int) (-inches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRightDrive.getCurrentPosition() + (int) (-inches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeftDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRightDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);

            robot.frontLeftDrive.setTargetPosition(newFrontLeftTarget);
            robot.frontRightDrive.setTargetPosition(newFrontRightTarget);
            robot.backLeftDrive.setTargetPosition(newBackLeftTarget);
            robot.backRightDrive.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            double power = Math.abs(speed);

            robot.frontLeftDrive.setPower(power);
            robot.frontRightDrive.setPower(power);
            robot.backLeftDrive.setPower(power);
            robot.backRightDrive.setPower(power);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy())) {
                int whereFrontRightThinks = robot.frontLeftDrive.getCurrentPosition();
                int whereFrontLeftThinks = robot.frontRightDrive.getCurrentPosition();
                int whereBackRightThinks = robot.backLeftDrive.getCurrentPosition();
                int whereBackLeftThinks = robot.backRightDrive.getCurrentPosition();

                telemetry.addData("front right position: ", whereFrontRightThinks);
                telemetry.addData("front left position: ", whereFrontLeftThinks);
                telemetry.addData("back right position: ", whereBackRightThinks);
                telemetry.addData("back left position: ", whereBackLeftThinks);

                telemetry.update();
            }

            // Stop all motion;
            robot.frontLeftDrive.setPower(0);
            robot.frontRightDrive.setPower(0);
            robot.backLeftDrive.setPower(0);
            robot.backRightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}