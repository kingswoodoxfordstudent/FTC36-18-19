package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;

public class bestteamcode4point3 {
    public DcMotor frontRightDrive = null;
    public DcMotor frontLeftDrive = null;
    public DcMotor backRightDrive = null;
    public DcMotor backLeftDrive = null;
    public DcMotor bigLift = null;
    public DcMotor loganPaulerLift = null;

    HardwareMap hwMap = null;

    // Detector object
    public GoldAlignDetector detector;

    public void init (HardwareMap hardwareMap){
        hwMap = hardwareMap;

        /*map motors for the configuration file */
        //drive train mapping
        frontRightDrive = hwMap.dcMotor.get("front_right_drive");
        frontLeftDrive = hwMap.dcMotor.get("front_left_drive");
        backRightDrive = hwMap.dcMotor.get("back_right_drive");
        backLeftDrive = hwMap.dcMotor.get("back_left_drive");
        bigLift = hwMap.dcMotor.get("big_lift");
        loganPaulerLift = hwMap.dcMotor.get("logan_pauler_lift");

        // set motor default power to 0 for the motors and default position to .5 for the servo
        frontRightDrive.setPower(0);
        frontLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        bigLift.setPower(0);
        loganPaulerLift.setPower(0);

        // just to make sure all the motors are running forward
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        bigLift.setDirection(DcMotor.Direction.FORWARD);
        loganPaulerLift.setDirection(DcMotor.Direction.FORWARD);

        // set all to run without encoder
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bigLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        loganPaulerLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
