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
    public static final double HRMIDDLE = 0.695;
    public static final double HRLEFT = 0.3539;
    public static int running = 0;
//    public static final double HRRIGHT = 1.0;
    boolean call = true;
    boolean call1 = true;
    boolean LPushed = false;
    boolean RPushed = false;
    StateMachine stateMachine;
    private Servo claw, hrclaw, rclaw;

    private DcMotor frontLeft, frontRight, backLeft, backRight, slider, rslider;
    boolean aButtonPreviousState = false;
    boolean slowModeActive = false;
    static final double MOTOR_TICKS_COUNT = 537.7;

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
        running = 0;
    }

    @Override
    public void loop() {
        StateMachine.LiftState liftState = stateMachine.getState();
        if (running == 0){
            rclaw.setPosition(RUP);
            running = 10000;

        }
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
        if(gamepad1.x){
            stateMachine.setState(StateMachine.LiftState.SUB);
            stateMachine.runState(liftState);
        }
        if(gamepad1.right_bumper && gamepad1.left_bumper){
            stateMachine.setState(StateMachine.LiftState.HOME);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_right && !gamepad1.dpad_left){
            stateMachine.setState(StateMachine.LiftState.TESTHIGH);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_down  && !gamepad1.dpad_up && !gamepad1.dpad_right && !gamepad1.dpad_left){
            stateMachine.setState(StateMachine.LiftState.TESTLOW);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_right && !gamepad1.dpad_down && !gamepad1.dpad_up && !gamepad1.dpad_left){
            stateMachine.setState(StateMachine.LiftState.TESTHIGHS);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_left && !gamepad1.dpad_down && !gamepad1.dpad_right && !gamepad1.dpad_up){
            stateMachine.setState(StateMachine.LiftState.TESTLOWS);
            stateMachine.runState(liftState);
        }
        if(gamepad1.dpad_left && gamepad1.dpad_down && gamepad1.dpad_right && gamepad1.dpad_up){
            stateMachine.setState(StateMachine.LiftState.MIDDLE);
            stateMachine.runState(liftState);
        }
        if(gamepad1.b){
            stateMachine.setState(StateMachine.LiftState.EXTEND);
           stateMachine.runState(liftState);
        }
        if(gamepad1.a){
            stateMachine.setState(StateMachine.LiftState.PICKUP);
            stateMachine.runState(liftState);
        }
        if(gamepad1.y){
            stateMachine.setState(StateMachine.LiftState.BASKET);
            stateMachine.runState(liftState);
        }
        if (gamepad1.left_bumper && !gamepad1.right_bumper) {
            LPushed = true;
        }
        if (LPushed && !gamepad1.left_bumper) {
            LPushed = false;
            if(call){
                claw.setPosition(CLAW_OPEN);
                call = false;
                return;
            }
            else if(!call){
                claw.setPosition(CLAW_CLOSE);
                call = true;
                return;
            }
        }

        if(gamepad1.right_bumper && !gamepad1.left_bumper) {
            RPushed = true;
        }
        if(RPushed && !gamepad1.right_bumper) {
            RPushed = false;
            if (call1) {
                hrclaw.setPosition(HRMIDDLE);
                call1 = false;
                return;
            } else if (!call1) {
                hrclaw.setPosition(HRLEFT);
                call1 = true;
                return;
            }
        }

        telemetry.addData("claw",claw.getPosition());
        telemetry.addData("rotating claw",rclaw.getPosition());
        telemetry.addData("rotating horizontal claw",hrclaw.getPosition());
        telemetry.addData("rotating slider", rslider.getCurrentPosition());
        telemetry.addData("slider", slider.getCurrentPosition());
        telemetry.update();
    }

}