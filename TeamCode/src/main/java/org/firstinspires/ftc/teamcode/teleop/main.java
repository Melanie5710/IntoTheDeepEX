package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "teleopEXTREME")
public class main extends OpMode {
    //diff version for diff chassis
    //rs = 113
    //S = IDK
    public static final double CLAW_CLOSE = 0.5;
    public static final double CLAW_OPEN = 0.63;
    public static final double UP = 0.5167;
    public static final double RDOWN = 0.0289;
    public static final double RUP = 0.7044;
    public static final double HRMIDDLE = 0.6739;
    public static final double HRLEFT = 0.3539;
    public static final double HRRIGHT = 1.0;
    StateMachine stateMachine;
    private Servo claw, hrclaw, rclaw;

    private DcMotor frontLeft, frontRight, backLeft, backRight, slider, rslider;
    boolean aButtonPreviousState = false;
    boolean slowModeActive = false;
    static final double MOTOR_TICKS_COUNT = 537.7;
    boolean call = true;
    boolean call1 = true;
    boolean LPushed = false;
    boolean RPushed = false;
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        stateMachine = new StateMachine(hardwareMap);
        stateMachine.init();
        frontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        backLeft = hardwareMap.get(DcMotorEx.class, "leftBack");
        backRight = hardwareMap.get(DcMotorEx.class, "rightBack");
        slider = hardwareMap.get(DcMotorEx.class, "slider");
        rslider = hardwareMap.get(DcMotorEx.class, "rslider");
        claw = hardwareMap.get(Servo.class, "claw");
        rclaw = hardwareMap.get(Servo.class, "rclaw");
        hrclaw = hardwareMap.get(Servo.class, "hrclaw");
        claw.setDirection(Servo.Direction.REVERSE);
        rclaw.setDirection(Servo.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        rclaw.setPosition(UP);
//        claw.setPosition(CLAW_CLOSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        StateMachine.LiftState liftState = stateMachine.getState();
        //
        double drive = -gamepad1.left_stick_y * 0.5;
        double strafe = -gamepad1.left_stick_x * 0.5;
        double turn = -gamepad1.right_stick_x * 0.5;

        double frontLeftPower = Range.clip(drive - strafe - turn, -1, 1);
        double frontRightPower = Range.clip(drive + strafe + turn, -1, 1);
        double backLeftPower = Range.clip(-drive - strafe + turn, -1, 1);
        double backRightPower = Range.clip(drive - strafe + turn, -1, 1);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);


//        StateMachine.LiftState liftState = stateMachine.getState();
//        if(gamepad2.dpad_left){
//            stateMachine.setState(StateMachine.LiftState.DROPOFF);
//            stateMachine.runState(liftState);
//        }
//        if(gamepad2.y){
//            stateMachine.setState(StateMachine.LiftState.SLIDERUP);
//            stateMachine.runState(liftState);
//        }
//        if(gamepad2.x){
//            stateMachine.setState(StateMachine.LiftState.LEVEL1);
//            stateMachine.runState(liftState);
//        }
//        if(gamepad2.a){
//            stateMachine.setState(StateMachine.LiftState.STAGEHOME);
//            stateMachine.runState(liftState);
//        }
//        if(gamepad2.b){
//            stateMachine.setState(StateMachine.LiftState.HANG);
//            stateMachine.runState(liftState);
//        }
//
        if(gamepad2.x){
            stateMachine.setState(StateMachine.LiftState.STRAIGHTFORWARD);
            stateMachine.runState(liftState);
        }
        if(gamepad2.y){
            stateMachine.setState(StateMachine.LiftState.UPWARD);
            stateMachine.runState(liftState);
        }
        if(gamepad2.a){
            stateMachine.setState(StateMachine.LiftState.STAGEHOME);
            stateMachine.runState(liftState);
        }
        if(gamepad2.left_bumper){
            claw.setPosition(CLAW_OPEN);
        }
        if(gamepad2.right_bumper){
            claw.setPosition(CLAW_CLOSE);
        }
//        if(gamepad2.dpad_up){
//            rclaw.setPosition(rclaw.getPosition() + 0.0005);
//        }
//        if(gamepad2.dpad_down){
//            rclaw.setPosition(rclaw.getPosition() - 0.0005);
//        }
        if(gamepad1.dpad_up){
            stateMachine.setState(StateMachine.LiftState.TESTHIGH);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_down){
            stateMachine.setState(StateMachine.LiftState.TESTLOW);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_right){
            stateMachine.setState(StateMachine.LiftState.TESTHIGHS);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_left){
            stateMachine.setState(StateMachine.LiftState.TESTLOWS);
            stateMachine.runState(liftState);
        }
        if(gamepad1.b){
            rclaw.setPosition(RUP);
        }
        if(gamepad1.x){
            rclaw.setPosition(RDOWN);
        }

        if(gamepad1.right_bumper){
            claw.setPosition(CLAW_CLOSE);
        }
        if(gamepad1.left_bumper){
            claw.setPosition(CLAW_OPEN);
        }
        if(gamepad1.y){
            hrclaw.setPosition(HRMIDDLE);
        }

//        if(gamepad1.dpad_right){
//            stateMachine.setState(StateMachine.LiftState.PICKUP);
//            stateMachine.runState(liftState);
//        }
        telemetry.addData("claw",claw.getPosition());
        telemetry.addData("rotating claw",rclaw.getPosition());
        telemetry.addData("rotating horizontal claw",hrclaw.getPosition());
        telemetry.addData("rotating slider", rslider.getCurrentPosition());
        telemetry.addData("slider", slider.getCurrentPosition());
        telemetry.update();
    }

}