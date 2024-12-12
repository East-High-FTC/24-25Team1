package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.CRServo;
import java.util.Map;
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
  
  //arm
  private DcMotor bottomarm; 
  private CRServo toparm;
  private Servo claw;
  private CRServo clawrotate;
  Boolean armClosed = false;
  

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    backleft = hardwareMap.get(DcMotor.class, "back left");
    backright = hardwareMap.get(DcMotor.class, "back right");
    frontleft = hardwareMap.get(DcMotor.class, "front left");
    frontright = hardwareMap.get(DcMotor.class, "front right");
    //arm
    bottomarm = hardwareMap.get(DcMotor.class, "bottomarm");
    toparm = hardwareMap.get(CRServo.class, "toparm");
    claw = hardwareMap.get(Servo.class, "claw");
    clawrotate = hardwareMap.get(CRServo.class, "clawrotate");

    // Put initialization blocks here. aka configuring motor stuff
    initialization();
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    double lyJoyStickPos = 0;
    double ryJoyStickPos = 0;
    double lxJoyStickPos = 0;
    double rxJoyStickPos = 0;
    //arm position
    double lyJoyStickPosARM = 0;
    double ryJoyStickPosARM = 0;
    double lxJoyStickPosARM = 0;
    double rxJoyStickPosARM = 0;
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        // Put loop blocks here.
        setUpInputs(ryJoyStickPos, rxJoyStickPos, lyJoyStickPos, lxJoyStickPos);
        telemetry.addData("Status", "Running");
        telemetry.update();
        // this is arm stuff
        setUpArmInputs(ryJoyStickPosARM, rxJoyStickPosARM, lyJoyStickPosARM, lxJoyStickPosARM);

        if(ryJoyStickPosARM > 0)
        {
          rotateClawCounterClockwise();
        }
        else if (ryJoyStickPosARM < 0)
        {
          rotateClawClockwise();
        }
        if(this.gamepad2.a && armClosed == false ) //close claw
        {
          close();
        }
        else if (this.gamepad2.a && armClosed == true) // this makes the a button toggleable
        {
          open();
        }
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
      backleft.setPower(-power);
      backright.setPower(-power);
      frontleft.setPower(-power);
      frontright.setPower(-power);
     
      telemetry.addData("Motor Power", frontleft.getPower());
      telemetry.addData("Motor Power", frontright.getPower());
      telemetry.addData("Motor Power", backright.getPower());
      telemetry.addData("Motor Power", backleft.getPower());
    }
  }
  private void rightLeft(double power, double fpower)
  {
    if(power > 0 && (fpower > -.1 && fpower < 0.1))//left
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
    else if (power < 0 && (fpower > -.1 && fpower < 0.1))//right
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
   private void diagonalLeft(double power, double fpower)
  {
      if(power < 0 && (fpower > .2)) //diagonal left down
    {    
      //reset directions
      initialization();
      frontleft.setDirection(DcMotor.Direction.REVERSE);
      backright.setDirection(DcMotor.Direction.FORWARD);
     
      backleft.setPower(0);
      backright.setPower(power);
      frontleft.setPower(power);
      frontright.setPower(0);
    }
    else if (power < 0 && (fpower < -.2))//left up
    {
      //reset directions
      initialization();
      frontright.setDirection(DcMotor.Direction.FORWARD);
      backleft.setDirection(DcMotor.Direction.REVERSE);
      backleft.setPower(power);
      backright.setPower(0);
      frontleft.setPower(0);
      frontright.setPower(power);
    }
  }
  private void diagonalRight(double power, double fpower)
  {
      if(power > 0 && (fpower > .2)) //diagonal right down
    {    
      //reset directions
      initialization();
      frontright.setDirection(DcMotor.Direction.REVERSE);
      backleft.setDirection(DcMotor.Direction.FORWARD);
     
      backleft.setPower(power);
      backright.setPower(0);
      frontleft.setPower(0);
      frontright.setPower(power);
    }
  }
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
      telemetry.update();
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
    backleft.setPower(0);
    backleft.setPower(0);
    backleft.setPower(0);
  }
  // arm functions
    private void armInitialization() {
    bottomarm.setDirection(DcMotor.Direction.FORWARD);
    toparm.setPower(0);
    claw.setPosition(0);
    clawrotate.setPower(0);
  }
  private void close() //claw
  {
    armClosed = true;
    claw.setPosition(1.d);
    telemetry.addData("Claw Status: ", claw.getPosition());
    telemetry.update();
  }
  private void open() // this servo is 180 degrees so its positions shall be 0-1 claw
  {
    armClosed = false;
    claw.setPosition(0.d); // in theory its 0 or 1 but we have to test it.
    telemetry.addData("Claw Status: ", claw.getPosition());
    telemetry.update();
  }
  private void rotateClawClockwise() // kreepy klaw thing
  {
    clawrotate.setPower(1.d);
  }
  private void rotateClawCounterClockwise()
  {
    clawrotate.setPower(-1.d);
  }
  private void moveArm(double powerarm)
  {
    if(powerarm > 0)
    {
      bottomarm.setDirection(DcMotor.Direction.REVERSE); // reverse is is forward since mootor is backwards
      bottomarm.setPower(powerarm *0.5);
    }
    else if(powerarm < 0)
    {
      bottomarm.setDirection(DcMotor.Direction.FORWARD); // forward is reverse since motor is backwards
      bottomarm.setPower(powerarm *0.5);
    }
  }
  private void moveTopArm(double powerarm)
  {
    if(powerarm > 0) // raises arm
    {
      toparm.setPower(powerarm * 2);
    }
    else if(powerarm < 0) // lowers arm
    {
      toparm.setPower(powerarm * 2);
    }
  }
  //make up and down on the left stick move the top half of the arm 
  //make up and down on the right stick move the bottom half of the arm 
  private void setUpArmInputs(double ryJoyStickPosARM, double rxJoyStickPosARM,
                              double lyJoyStickPosARM, double lxJoyStickPosARM)
{
    ryJoyStickPosARM = this.gamepad2.right_stick_y;
    rxJoyStickPosARM = -this.gamepad2.right_stick_x;
    lxJoyStickPosARM = -this.gamepad2.left_stick_x;
    lyJoyStickPosARM = -this.gamepad2.left_stick_y;
   
    moveArm(ryJoyStickPosARM);
    moveTopArm(lyJoyStickPosARM); // let us hope this works!
    //telemetry.addData("Go Power:", ryJoyStickPos);
    //telemetry.addData("Rotate Power:", lxJoyStickPos); //theoretikal telemaetry
    telemetry.update();
  }
}
