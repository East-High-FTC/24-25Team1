package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "SkibidiDrive (Blocks to Java)")
public class SkibidiDrive extends LinearOpMode {

  private DcMotor backleft;
  private DcMotor backright;
  private DcMotor frontleft;
  private DcMotor frontright;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    backleft = hardwareMap.get(DcMotor.class, "back left");
    backright = hardwareMap.get(DcMotor.class, "back right");
    frontleft = hardwareMap.get(DcMotor.class, "front left");
    frontright = hardwareMap.get(DcMotor.class, "front right");

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
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        // Put loop blocks here.
        setUpInputs();
        telemetry.addData("Status", "Running");
        telemetry.update();
      // Put run blocks here.
     
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
    if(power < 0 && (fpower > -.1 && fpower < 0.1))//left
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
    else if (power > 0 && (fpower > -.1 && fpower < 0.1))//right
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
        backleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);

    }
    else if (power <0) // kounter klock wise
    {
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.FORWARD);
    }
    //set power
    backleft.setPower(Math.abs(power));
    backright.setPower(Math.abs(power));
    frontleft.setPower(Math.abs(power));
    frontright.setPower(Math.abs(power));

    telemetry.addData("Rotation Power", power);
    telemetry.update();
  }
}
private void setUpInputs()
{
    ryJoyStickPos = this.gamepad1.right_stick_y;
    rxJoyStickPos = -this.gamepad1.right_stick_x;
    lxJoyStickPos = -this.gamepad1.left_stick_x;
    lyJoyStickPos = -this.gamepad1.left_stick_y;
   
    if(this.gamepad1.b)
    {
      bb();
      else
      {
          forwardBackward(ryJoyStickPos, rxJoyStickPos);
          rightLeft(rxJoyStickPos, ryJoyStickPos);
          rotate(lxJoyStickPos, lyJoyStickPos);
      }
    }
    telemetry.addData("Right Power:", ryJoyStickPos);
    //telemetry.addData("Rotate Power:", lxJoyStickPos); //theoretikal telemaetry
    telemetry.update();
  }
}
