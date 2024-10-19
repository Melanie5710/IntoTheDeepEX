package org.firstinspires.ftc.teamcode.teleoptest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class motortester extends OpMode {
    //diff version for diff chassis
    private DcMotor slider, rslider;
    //    private Servo claw, secondStage, intakeArm;
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        slider = hardwareMap.get(DcMotorEx.class, "slider");
        rslider = hardwareMap.get(DcMotorEx.class, "rslider");
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rslider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rslider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        claw.setPosition(0);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }

    @Override
    public void loop() {
        if(gamepad2.dpad_up){
            rslider.setPower(1);
            rslider.setTargetPosition(rslider.getCurrentPosition() + 10);
            rslider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(gamepad2.dpad_down){
            rslider.setPower(1);
            rslider.setTargetPosition(rslider.getCurrentPosition() - 10);
            rslider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        telemetry.addData("rotating slider", rslider.getCurrentPosition());
        telemetry.addData("slider", slider.getCurrentPosition());
        telemetry.update();
    }

}