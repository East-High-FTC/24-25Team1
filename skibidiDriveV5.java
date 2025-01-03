/*
SUPER HELPFUL LINK ON ENCODERS!
https://gm0.org/en/latest/docs/software/tutorials/encoders.html
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "SkibidiDriveBuiltBackBetter (Blocks to Java)")
public class SkibidiDriveBuiltBackBetter extends LinearOpMode {

    private DcMotor backleft = null;
    private DcMotor backright = null;
    private DcMotor frontleft = null;
    private DcMotor frontright = null;
    //arm stuff
    private DcMotor bottomarm = null;
    private DcMotor toparm = null;
    private Servo claw = null;
    private CRServo clawrotate = null;
    Boolean clawClosed = false;
    private ElapsedTime runtime = new ElapsedTime();

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        //hardware initialization DONT FORGET OR ELSE REALLY LONG ERROR THAT IS ANNOYING SINCE ITS VERY AMBIGIOUS
        backleft = hardwareMap.get(DcMotor.class, "back left");
        backright = hardwareMap.get(DcMotor.class, "back right");
        frontleft = hardwareMap.get(DcMotor.class, "front left");
        frontright = hardwareMap.get(DcMotor.class, "front right");
        // arm
        bottomarm = hardwareMap.get(DcMotor.class, "bottomarm");
        toparm = hardwareMap.get(DcMotor.class, "toparm");
        claw = hardwareMap.get(Servo.class, "claw");
        clawrotate = hardwareMap.get(CRServo.class, "clawrotation");
        // Put initialization blocks here. aka configuring motor stuff
        initialization();
        armInitialization();
        //debounce
         long lastClawToggle = 0; // miliseconds also long or error
        final long clawToggleDelay = 500; // miliseconds
        //telemetry
        telemetry.addData("Status", "Drivebase Initialized");
        telemetry.addData("Status", "Arm Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset(); // heard thiss is good
       // if (opModeIsActive()) {
            while (opModeIsActive()) {
                telemetry.addData("Status","Run Time: " + runtime.toString());
                // Put loop blocks here.
                //player 1 controller variables
                double ryJoyStickPos = this.gamepad1.right_stick_y;
                double rxJoyStickPos = -this.gamepad1.right_stick_x;
                double lxJoyStickPos = -this.gamepad1.left_stick_x;
                double lyJoyStickPos = -this.gamepad1.left_stick_y;

                setUpInputs(ryJoyStickPos, rxJoyStickPos, lyJoyStickPos, lxJoyStickPos); //THIS IS MOTORS NOT ARM!!!!!!

                double ryJoyStickPosARM = this.gamepad2.right_stick_y;
                double lyJoyStickPosARM = this.gamepad2.left_stick_y;

                setUpArmInputs(ryJoyStickPosARM, lyJoyStickPosARM);

                telemetry.addData("Status", "Running");
                telemetry.update();
                //player 1 inputs (driver)
                if(this.gamepad1.b)
                {
                    telemetry.addData("Status:", "Broken!");
                    telemetry.update();
                    bb();
                }
                if(this.gamepad1.x)
                {
                    hitBreaks(); // is this break or the brake button?!?!?!?!?
                }
                //player 2 inputs (arm guy)
                if (gamepad2.right_bumper) // evan your right bumpers actually make sense (i too can admit when im wrong)
                {
                    rotateArmClockwise();
                }
                if (gamepad2.left_bumper)
                {
                    rotateArmCounterClockwise();
                }
                if(!gamepad2.left_bumper && !gamepad2.right_bumper)
                {
                    clawrotate.setPower(0); // Stop claw rotation
                }
                if(this.gamepad2.a &&(System.currentTimeMillis() - lastClawToggle > clawToggleDelay)) // this makes it toggle able
                {
                    clawClosed = !clawClosed; // better debounce
                    lastClawToggle = System.currentTimeMillis(); // so basically a round about way to get rid of sleep since it can cause a delay
                    if(clawClosed)
                    {
                        close();
                    }
                    else
                    {
                        open();
                    }
                }
                // Put run blocks here.
            }
       // }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // setting up motors
    private void initialization() {
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.FORWARD);
    }

    //Forward & backwards movement
    private void forwardBackward(double power, double dpower) {
        initialization();
        if(dpower == 0)
        {
            backleft.setPower(power);
            backright.setPower(power);
            frontleft.setPower(power);
            frontright.setPower(power);

            telemetry.addData("Motor Power", frontleft.getPower());
            telemetry.addData("Motor Power", frontright.getPower());
            telemetry.addData("Motor Power", backright.getPower());
            telemetry.addData("Motor Power", backleft.getPower());
        }
    }
    private void rightLeft(double power, double fpower)
    {
        if(power < 0 && (fpower > -.2 && fpower < 0.2))//left
        {
            //reset directions
            initialization();
            frontleft.setDirection(DcMotor.Direction.REVERSE);
            backright.setDirection(DcMotor.Direction.FORWARD);

            backleft.setPower(power);
            backright.setPower(power);
            frontleft.setPower(power);
            frontright.setPower(power);
        }
        else if (power > 0 && (fpower > -.2 && fpower < 0.2))//right
        {
            //reset directions
            initialization();
            frontleft.setDirection(DcMotor.Direction.REVERSE);
            backright.setDirection(DcMotor.Direction.FORWARD);
            backleft.setPower(power);
            backright.setPower(power);
            frontleft.setPower(power);
            frontright.setPower(power);
        }
    }
    //stuff evan made
    private void diagonalLeft(double power, double lpower)
    {
        if(power < -.2 && (lpower > .2)) //diagonal left down
        {
            //reset directions
            initialization();
            frontleft.setDirection(DcMotor.Direction.REVERSE);
            backright.setDirection(DcMotor.Direction.FORWARD);

            backleft.setPower(0);
            backright.setPower(power + lpower);
            frontleft.setPower(power + lpower);
            frontright.setPower(0);
        }
        else if (power < -.2 && (lpower < -.2))//left up
        {
            //reset directions
            initialization();
            frontright.setDirection(DcMotor.Direction.REVERSE);
            backleft.setDirection(DcMotor.Direction.FORWARD);
            backleft.setPower(power + lpower);
            backright.setPower(0);
            frontleft.setPower(0);
            frontright.setPower(power + lpower);
        }
    }
    private void diagonalRight(double power, double lpower)
    {
        if(power > .2 && (lpower > .2)) //diagonal right down
        {
            //reset directions
            initialization();
            frontright.setDirection(DcMotor.Direction.FORWARD);
            backleft.setDirection(DcMotor.Direction.REVERSE);

            backleft.setPower(power + lpower);
            backright.setPower(0);
            frontleft.setPower(0);
            frontright.setPower(power + lpower);
        }
        else if (power > .2 && (lpower < -.2))//right up
        {
            //reset directions
            initialization();
            frontleft.setDirection(DcMotor.Direction.FORWARD);
            backright.setDirection(DcMotor.Direction.REVERSE);
            backleft.setPower(0);
            backright.setPower(power + lpower);
            frontleft.setPower(power + lpower);
            frontright.setPower(0);
        }
    }
    //evan didnt write this
    private void bb()
    {
        double power = 0;
        backleft.setPower(power);
        backright.setPower(power);
        frontleft.setPower(power);
        frontright.setPower(power);
        while(true)
        {
            telemetry.addData("need some help now!!!", power);

        }
    }
    private void rotate(double xpower, double ypower)
    {
        initialization();
        double power = Math.max(-1.0, Math.min(1.0, xpower));

        //set motor directions for rotation & clockwise/counter clockwise rotation
        if(power > 0) //qlock wise
        {
            frontleft.setDirection(DcMotor.Direction.FORWARD);
            backleft.setDirection(DcMotor.Direction.REVERSE); //back wheels are reversed for some reason idk why just don't this or it will break IM TELLING YOU PLS PLS IOguawedghasdfuih us
            frontright.setDirection(DcMotor.Direction.REVERSE);
            backright.setDirection(DcMotor.Direction.FORWARD); //gear is backwards which is why we have the thing go forwards for reverse (trust guys)
        }
        else if (power <0) // kounter klock wise
        {
            frontleft.setDirection(DcMotor.Direction.REVERSE);
            backleft.setDirection(DcMotor.Direction.FORWARD); //back wheels are reversed for some reason idk why just don't this or it will break IM TELLING YOU PLS PLS IOguawedghasdfuih us
            frontright.setDirection(DcMotor.Direction.FORWARD);
            backright.setDirection(DcMotor.Direction.REVERSE); //gear is backwards which is why we have the thing go forwards for reverse (trust guys)
        }
        //set power
        backleft.setPower(Math.abs(power));
        backright.setPower(Math.abs(power));
        frontleft.setPower(Math.abs(power));
        frontright.setPower(Math.abs(power));

        telemetry.addData("Rotation Power", power);
        telemetry.update();
    }
    private void setUpInputs(double ryJoyStickPos, double rxJoyStickPos,
                             double lyJoyStickPos, double lxJoyStickPos)
    {
        forwardBackward(ryJoyStickPos, rxJoyStickPos);
        rightLeft(rxJoyStickPos, ryJoyStickPos);
        diagonalRight(rxJoyStickPos, ryJoyStickPos); //this is probably wrong
        diagonalLeft(rxJoyStickPos, ryJoyStickPos); //this is probably wrong
        rotate(lxJoyStickPos, lyJoyStickPos);
        telemetry.addData("Go Power:", ryJoyStickPos);
        //telemetry.addData("Rotate Power:", lxJoyStickPos); //theoretikal telemaetry
        telemetry.update();
    }
    private void hitBreaks()
    {
        telemetry.addData("Status","Emergency Breaks Activated!");
        telemetry.update();
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
    }
    //ARM FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //setup motors dont forget to add the armInitialization() earlier
    private void armInitialization() {
        bottomarm.setDirection(DcMotor.Direction.FORWARD);
        toparm.setDirection(DcMotor.Direction.FORWARD);
        claw.setPosition(1);
        //clawrotate.setPower(0);
    }
    private void close()
    {
        claw.setPosition(1);
    }
    private void open() // this servo is 180 degrees so its positions shall be 0-1
    {
        claw.setPosition(0); // in theory its 0 or 1 but we have to test it.
    }
    private void rotateArmClockwise()
    {
        clawrotate.setPower(0.5);
    }
    private void rotateArmCounterClockwise()
    {
        clawrotate.setPower(-0.5);
    }
    private void moveArm(double powerarm)
    {
        bottomarm.setDirection(DcMotor.Direction.REVERSE);
        bottomarm.setPower(powerarm *0.75);
    }
    private void moveForearm(double power)
    {
        toparm.setPower(power *.4);
    }
    //make up and down on the left stick move the top half of the arm
    //make up and down on the right stick move the bottom half of the arm
    private void setUpArmInputs(double ryJoyStickPosARM, double lyJoyStickPosARM)
    {
        moveArm(ryJoyStickPosARM);
        moveForearm(lyJoyStickPosARM);
        telemetry.addData("Bottom Arm Power:", ryJoyStickPosARM);
        telemetry.addData("Top Arm Power:", lyJoyStickPosARM);
        telemetry.update();
    }
}
