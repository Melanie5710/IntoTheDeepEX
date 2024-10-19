package org.firstinspires.ftc.teamcode.teleoptest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class servotester extends OpMode {
    //diff version for diff chassis
    private Servo claw, hrclaw, rclaw;
    //    private Servo claw, secondStage, intakeArm;
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        claw = hardwareMap.get(Servo.class, "claw");
        rclaw = hardwareMap.get(Servo.class, "rclaw");
        hrclaw = hardwareMap.get(Servo.class, "hrclaw");
        claw.setDirection(Servo.Direction.REVERSE);
        rclaw.setDirection(Servo.Direction.REVERSE);
//        claw.setPosition(0);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }

    @Override
    public void loop() {
        if(gamepad2.x){

        }
        if(gamepad2.dpad_up){
            rclaw.setPosition(rclaw.getPosition() + 0.0005);
        }
        if(gamepad2.dpad_down){
            rclaw.setPosition(rclaw.getPosition() - 0.0005);
        }
//        if(gamepad2.dpad_left){
//            airplane.setPosition(airplane.getPosition() + 0.0005);
//        }
//        if(gamepad2.dpad_right){
//            airplane.setPosition(airplane.getPosition() - 0.0005);
//        }
//        if(gamepad2.dpad_up){
////            dropBox.setPosition(dropBox.getPosition() + 0.0005);
//            intakeArm.setPosition(intakeArm.getPosition() + 0.0005);
//        }
//        if(gamepad2.dpad_down){
////            dropBox.setPosition(dropBox.getPosition() - 0.0005);
//            intakeArm.setPosition(intakeArm.getPosition() - 0.0005);
//        }
        telemetry.addData("claw",claw.getPosition());
        telemetry.addData("rotating claw",rclaw.getPosition());
        telemetry.addData("rotating horizontal claw",hrclaw.getPosition());
        telemetry.update();
    }

}