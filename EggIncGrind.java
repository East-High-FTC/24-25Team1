//arm code cause i said i would make it and now i regret it
/**
IMPORTANT LINK TO TEACH YOU SERVOS 
https://ftc-docs.firstinspires.org/en/latest/programming_resources/tutorial_specific/onbot_java/controlling_a_servo/Controlling-a-Servo-%28OnBot-Java%29.html
!!! READ DESCRIPTION !!!
(The three dots next to the file name on git hub)
**/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "EggIncGrind (Blocks to Java)")
public class EggIncGrind extends LinearOpMode {
  private DcMotor bottomarm;
  private DcMotor toparm;
  private Servo claw;
  private CRServo clawrotate;
  Boolean clawClosed = false;
  @Override
  public void runOpMode() {
    
    bottomarm = hardwareMap.get(DcMotor.class, "bottom arm");
    toparm = hardwareMap.get(DcMotor.class, "top arm");
    claw = hardwareMap.get(Servo.class, "claw");
    clawrotate = hardwareMap.get(CRServo.class, "claw rotation");

    // Put initialization blocks here. aka configuring motor stuff
    armInitialization();
    telemetry.addData("Status", "Servors Initialized");
    telemetry.update();
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    double lyJoyStickPosArm = 0;
    double ryJoyStickPosArm = 0;
    double lxJoyStickPosArm = 0;
    double rxJoyStickPosArm = 0;
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        ryJoyStickPosARM = this.gamepad2.right_stick_y;
        lyJoyStickPosARM = this.gamepad2.left_stick_y;
        setUpInputs(ryJoyStickPos, rxJoyStickPos, lyJoyStickPos, lxJoyStickPos); // THIS IS MOTOR I FORGOR I DELETED THIS DON'T DO THAT THAT BREAKS THE DRIVE HUB AND WAS PEROBABLY THE REASON IT STARTED DRIVING BY ITS SELF THAT ONE TIME!!!!!!!!!!!!
        setUpArmInputs(ryJoyStickPosARM, lyJoyStickPosARM);

        if (gamepad2.right_bumper) // evan your right bumpers actually make sense (i now can admit when i was wrong)
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
            }
            else
            {
                open();
            }
        }
    }
  }
}

//use this after runopmode

//setup motors dont forget to add the armInitialization() earlier
 private void armInitialization() {
    bottomarm.setDirection(DcMotor.Direction.FORWARD);
    toparm.setDirection(DcMotor.Direction.FORWARD);
    claw.setPosition(0);
    clawrotate.setPosition(0);
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
    if(powerarm < 0 )
    {
      bottomarm.setDirection(DcMotor.Direction.REVERSE);
      bottomarm.setPower(powerarm * 0.5);
    }
    else if(powerarm > 0)
    {
      bottomarm.setDirection(DcMotor.Direction.FORWARD);
      bottomarm.setPower(powerarm * 0.5);
    }
  }
  private void moveForearm(power)
  {
    toparm.setPower(power *.5);
  }
  //make up and down on the left stick move the top half of the arm 
  //make up and down on the right stick move the bottom half of the arm 
  private void setUpArmInputs(double ryJoyStickPosARM, double lyJoyStickPosARM)
{  
    moveArm(ryJoyStickPosARM);
    moveForearm(lyJoyStickPosARM);
    telemetry.addData("Bottom Arm Power:", ryJoyStickPos);
    telemetry.addData("Top Arm Power:", lxJoyStickPos); //theoretikal telemaetry
    telemetry.update();
  }
}
