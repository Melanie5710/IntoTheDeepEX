package org.firstinspires.ftc.teamcode.teleop;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import android.os.SystemClock;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StateMachine{
    public static final double CLAW_CLOSE = 0.5;
    public static final double CLAW_OPEN = 0.63;
    public static final double UP = 0.5167;
    public static final double RDOWN = 0.0289;
    public static final double RUP = 0.7044;
    public static final double HRMIDDLE = 0.695;
    public static final double HRLEFT = 0.3539;
    public static final boolean wait = true;
    private ElapsedTime runtime = new ElapsedTime();
    public static Telemetry telemetry;
    protected HardwareMap hwMap;

    public StateMachine(HardwareMap hwMap) {
        this.hwMap = hwMap;
    }

    public enum LiftState {
        MIDDLE,
        SUB,
        PICKUP,
        BASKET,
        TESTHIGH,
        TESTLOW,
        TESTHIGHS,
        TESTLOWS,
        EXTEND,
        HOME,
    }

    public void setState(LiftState state) {
        this.liftState = state;
    }

    public LiftState getState() {
        return this.liftState;
    }

    private LiftState liftState;
        private Servo claw, rclaw, hrclaw;
    private DcMotor frontLeft, frontRight, backLeft, backRight, slider, rslider;

    public void init() {
        frontLeft = hwMap.get(DcMotorEx.class, "leftFront");
        frontRight = hwMap.get(DcMotorEx.class, "rightFront");
        backLeft = hwMap.get(DcMotorEx.class, "leftBack");
        backRight = hwMap.get(DcMotorEx.class, "rightBack");
        slider = hwMap.get(DcMotorEx.class, "slider");
        rslider = hwMap.get(DcMotorEx.class, "rslider");
        claw = hwMap.get(Servo.class, "claw");
        rclaw = hwMap.get(Servo.class, "rclaw");
        hrclaw = hwMap.get(Servo.class, "hrclaw");
        claw.setDirection(Servo.Direction.REVERSE);
        rclaw.setDirection(Servo.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rslider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rslider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rclaw.setPosition(0.87);
        hrclaw.setPosition(HRMIDDLE);
        runtime.startTime();
//        liftState = LiftState.STAGE HOME;

    }

    public void runState(LiftState previousState) {
        switch (liftState) {
            case MIDDLE:
                middle();
                break;
            case SUB:
                sub();
                break;
            case EXTEND:
                extend();
                break;
            case BASKET:
                basket();
                break;
            case PICKUP:
                pickup();
                break;
            case TESTHIGH:
                testhigh();
                break;
            case TESTLOW:
                testlow();
                break;
            case TESTHIGHS:
                testhighs();
                break;
            case TESTLOWS:
                testlows();
                break;
            case HOME:
                stagehome();
                break;
            default:
                break;
        }
    }
    public void middle(){
        moveToTargetrStage(-825,0.8);
    }
    public void sub(){
        moveToTargetrStage(-1972, 0.5);
        //-2153
        runtime.reset();
        while (wait) {
            if (runtime.seconds() > 1.3) {
                moveToTargetStage(-2343,0.8);
                //-1770
                rclaw.setPosition(RDOWN);
                break;
            }
        }
    }
    public void pickup() {
//        moveToTargetrStage(-680, 0.2);
        claw.setPosition(CLAW_CLOSE);
//        runtime.reset();
//        while (wait) {
//            if (runtime.seconds() > 1) {
//                claw.setPosition(CLAW_CLOSE);
//                break;
//            }
//        }
        runtime.reset();
        while (wait) {
            if (runtime.seconds() > 0.5) {
                moveToTargetrStage(-825,0.8);
                break;
            }
        }
        runtime.reset();
        while (wait) {
            if (runtime.seconds() > 0.5) {
                moveToTargetStage(-10,1.0);
                break;
            }
        }
    }
    public void basket() {
        moveToTargetrStage(-2629, 0.5);
        runtime.reset();
        while (wait) {
            if (runtime.seconds() > 1.3) {
                moveToTargetStage(-5937,1.0);
                rclaw.setPosition(RUP);
                break;
            }
        }
    }

    public void extend(){
        moveToTargetrStage(-680, 0.4);
        runtime.reset();
        while (wait) {
            if (runtime.seconds() > 0.8) {
                moveToTargetStage(-3708,1.0);
                claw.setPosition(CLAW_OPEN);
                break;
            }
        }
        rclaw.setPosition(RDOWN);
    }
    public void stagehome(){
        moveToTargetStage(-10,1.0);
//        runtime.reset();
//        while (wait) {
//            if (runtime.seconds() > 3) {
////                moveToTargetrStage(373,0.4);
//                break;
//            }
//        }
        rclaw.setPosition(RUP);
    }
    public void testhigh() {
        moveToTargetrStage(rslider.getCurrentPosition() - 50, 1);
    }

    public void testlow() {
        moveToTargetrStage(rslider.getCurrentPosition() + 50, 1);
    }

    public void testhighs() {
        moveToTargetStage(slider.getCurrentPosition() - 100, 1);
    }

    public void testlows() {
        moveToTargetStage(slider.getCurrentPosition() + 100, 1);
    }
    public void moveToTargetStage(int target, double power) {
        slider.setPower(power);
        slider.setTargetPosition(target);
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveToTargetrStage(int target, double power) {
        rslider.setPower(power);
        rslider.setTargetPosition(target);
        rslider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}



