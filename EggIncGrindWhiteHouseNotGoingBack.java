package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Autonomous Blue") // THIS IS FOR WHEN WE ARE BLUE TEAM!! VERY IMPORTANT IF YOU DIDN'T READ THIS THEN YOU WOULD THINK THIS IS RED TEAM OR SOMETHING THEN NWE WOULD LOSE AND THAT IS VERY BAD
public class EggIncGrindHouse extends LinearOpMode {

    // adding motors for driving
    private DcMotor backleft; 
    private DcMotor backright;
    private DcMotor frontleft;
    private DcMotor frontright;
    // so i saw something saying we need to convert from the counts per revolution of the encoder to counts per inch
    // some distancing stuff i guess so add that here:
    //WE NEED ENCODERS!!!!!
    /*
     * static final double COUNTS_PER_INCH = do math here lel im not doing allat :SKULL:
     * static final double COUNTS_PER_REVOLUTION = figure that out since we need to find the specs
     */
    @Override
    public void runOpMode()
    {
        double robotSpeed = 0.5; // this is the speed in which the robot moves at

        backleft = hardwareMap.get(DcMotor.class, "back left");
        backright = hardwareMap.get(DcMotor.class, "back right");
        frontleft = hardwareMap.get(DcMotor.class, "front left");
        frontright = hardwareMap.get(DcMotor.class, "front right");
        initialization();

        waitForStart();
        
        backward(1000);
        left(500);
        forward(1000);
        backward(1000);
        left(500);
        forward(1000);
        backward(1000);
        left(500);
        forward(1000);
        backward(1000);

    }    
    private void forward(double time)
    {
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.FORWARD);
        sleep(100);
        backleft.setPower(0.5);
        backright.setPower(0.5);
        frontleft.setPower(0.5);
        frontright.setPower(0.5);
        sleep(time);
    }    
    private void backward(double time)
    {
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.FORWARD);
        backleft.setDirection(DcMotor.Direction.REVERSE);
        sleep(100);
        backleft.setPower(0.5);
        backright.setPower(0.5);
        frontleft.setPower(0.5);
        frontright.setPower(0.5);
        sleep(time);
    }
    private void right(double time)
    {
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);
        sleep(100);
        backleft.setPower(0.5);
        backright.setPower(0.5);
        frontleft.setPower(0.5);
        frontright.setPower(0.5);
        sleep(time);
    }
    private void left(double time)
    {
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.FORWARD);
        backleft.setDirection(DcMotor.Direction.FORWARD);
        sleep(100);
        backleft.setPower(0.5);
        backright.setPower(0.5);
        frontleft.setPower(0.5);
        frontright.setPower(0.5);
        sleep(time);
    }
    private void rotateclockwise(double time)
    {
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.FORWARD);
        backleft.setDirection(DcMotor.Direction.REVERSE);
        sleep(100);
        backleft.setPower(0.5);
        backright.setPower(0.5);
        frontleft.setPower(0.5);
        frontright.setPower(0.5);
        sleep(time);
    }
        private void rotatecounterclockwise(double time)
    {
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.FORWARD);
        sleep(100);
        backleft.setPower(0.5);
        backright.setPower(0.5);
        frontleft.setPower(0.5);
        frontright.setPower(0.5);
        sleep(time);
    }
    private void initialization() 
    {
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.FORWARD);
    }
}
