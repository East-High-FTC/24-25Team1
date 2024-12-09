//arm code cause i said i would make it and now i regret it
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "EggIncGrind (Blocks to Java)")
public class EggIncGrind extends LinearOpMode {
  private DcMotor bottomarm;
  private Servo toparm;
  private Servo claw;
  private Servo clawrotate;

  @Override
  public void runOpMode() {


    bottomarm = hardwareMap.get(DcMotor.class, "bottom arm");
    toparm = hardwareMap.get(Servo.class, "top arm");
    claw = hardwareMap.get(Servo.class, "claw");
    clawrotate = hardwareMap.get(Servo.class, "claw rotation");

//DONT COPY

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
        setUpInputs(ryJoyStickPos, rxJoyStickPos, lyJoyStickPos, lxJoyStickPos);
        setUpArmInputs(ryJoyStickPosARM, rxJoyStickPosARM, lyJoyStickPosARM, lxJoyStickPosARM);
        telemetry.addData("Status", "Running");
        telemetry.update();
    
//COPY


      
        if(this.gamepad2.right_trigger) //open claw
        {
          open();
        }

        if(this.gamepad2.left_trigger) //close claw
        {
          close();
        }
        if(this.gamepad2.right_bumper) //move claw
        {
          rotateClawCounterClockwise();
        }
        if(this.gamepad2.left_bumper) //move claw
        {
          rotateClawClockwise();
        }

    }
  }
}

//use this after runopmode

//setup motors dont forget to add the armInitialization() earlier
  private void armInitialization() {
    bottomarm.setDirection(DcMotor.Direction.FORWARD);
    toparm.setDirection(DcMotor.Direction.FORWARD);
    claw.setDirection(DcMotor.Direction.FORWARD);
    clawrotate.setDirection(DcMotor.Direction.FORWARD);
  }
  private void close()
  {
    claw.setPosition(Servo.Direction.FORWARD);
    claw.setPower(0.5);
  }
  private void open()
  {
    claw.setPosition(Servo.Direction.REVERSE);
    claw.setPower(0.5);
  }
  private void rotateArmClockwise()
  {
    clawrotate.setPosition(Servo.);
    clawrotate.setPower(0.5);
  }
  private void rotateArmCounterClockwise()
  {
    clawrotate.setDirection(Servo.Direction.REVERSE);
    clawrotate.setPower(0.5);
  }
  private void moveArm(double powerarm, double fpowerarm)
  {
    if(powerarm < 0 && (fpowerarm > -.1 && fpowerarm < 0.1))
    {
      bottomarm.setDirection(DcMotor.Direction.FORWARD);
      bottomarm.setPower(powerarm * 0.5);
    }
    else if(powerarm > 0 && (fpowerarm > -.1 && fpowerarm < 0.1))
    {
      bottomarm.setDirection(DcMotor.Direction.REVERSE);
      bottomarm.setPower(powerarm * 0.5);
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
   
    moveArm(ryJoyStickPosARM, rxJoyStickPosARM);
    moveForearm(lxJoyStickPosARM, lyJoyStickPosARM);
    //telemetry.addData("Go Power:", ryJoyStickPos);
    //telemetry.addData("Rotate Power:", lxJoyStickPos); //theoretikal telemaetry
    telemetry.update();
  }
}
