package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "SkibidiDriveBuiltBackBetter (Blocks to Java)")
public class SkibidiDriveBuiltBackBetter extends LinearOpMode {

  private DcMotor backleft;
  private DcMotor backright;
  private DcMotor frontleft;
  private DcMotor frontright;
  //arm stuff
  private DcMotor bottomarm;
  private DcMotor toparm;
  private Servo claw;
  private CRServo clawrotate;
  Boolean clawClosed = false;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
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
    telemetry.addData("Status", "Drivebase Initialized");
    telemetry.addData("Status", "Arm Initialized");
    telemetry.update();
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    double lyJoyStickPos = 0.0; // i forgor to add decimal (this will save 0.000001 second to compile)
    double ryJoyStickPos = 0.0;
    double lxJoyStickPos = 0.0;
    double rxJoyStickPos = 0.0;
    //arm
    double lyJoyStickPosARM = 0.0;
    double ryJoyStickPosARM = 0.0;
    
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        // Put loop blocks here.
        ryJoyStickPosARM = this.gamepad2.right_stick_y;
        lyJoyStickPosARM = this.gamepad2.left_stick_y;
        setUpInputs(ryJoyStickPos, rxJoyStickPos, lyJoyStickPos, lxJoyStickPos); //THIS IS MOTORS NOT ARM!!!!!!
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
        /*if(this.gamepad1.right_trigger)
        {
          motorSpeed = 0.5; // change
        }
        else
        {
          motorSpeed = 1.0; //change
        } */
        //player 2 inputs (arm guy)
        if (gamepad2.right_bumper) // evan your right bumpers actually make sense (i too can admit when im wrong)
        { 
            rotateArmClockwise();
        } 
        else if (gamepad2.left_bumper)
        {
            rotateArmCounterClockwise();
        }
        else 
        {
            clawrotate.setPower(0); // Stop claw rotation
        }
        if(this.gamepad2.a) // this makes it toggle able
        {
            clawClosed = !clawClosed; // better debounce
            if(clawClosed)
            {
                close();
                sleep(500);
            }
            else
            {
                open();
                sleep(500);
            }
        }
      // Put run blocks here.
      }
    }
  }

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
      frontright.setDirection(DcMotor.Direction.FORWARD);
      backleft.setDirection(DcMotor.Direction.REVERSE);
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
      frontright.setDirection(DcMotor.Direction.REVERSE);
      backleft.setDirection(DcMotor.Direction.FORWARD);
     
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
    ryJoyStickPos = this.gamepad1.right_stick_y;
    rxJoyStickPos = -this.gamepad1.right_stick_x;
    lxJoyStickPos = -this.gamepad1.left_stick_x;
    lyJoyStickPos = -this.gamepad1.left_stick_y;
   
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
    clawrotate.setPower(0);
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
    /*if(powerarm < 0 )
    {
      bottomarm.setDirection(DcMotor.Direction.REVERSE);
      bottomarm.setPower(powerarm * 0.5);
    }
    else if(powerarm > 0)
    {
      bottomarm.setDirection(DcMotor.Direction.FORWARD);
      bottomarm.setPower(powerarm * 0.5);
    }*/
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
