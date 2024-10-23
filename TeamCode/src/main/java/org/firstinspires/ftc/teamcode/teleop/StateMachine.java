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
    private ElapsedTime runtime = new ElapsedTime();
    public static Telemetry telemetry;
    protected HardwareMap hwMap;

    public StateMachine(HardwareMap hwMap) {
        this.hwMap = hwMap;
    }

    public enum LiftState {
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
        rclaw.setPosition(RUP);
        hrclaw.setPosition(HRMIDDLE);
        runtime.startTime();
//        liftState = LiftState.STAGE HOME;

    }

    public void runState(LiftState previousState) {
        switch (liftState) {
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
    public void sub(){
        moveToTargetrStage(977, 0.5);
        runtime.reset();
        while (slider.getCurrentPosition() != 5000000) {
            if (runtime.seconds() > 1.3) {
                moveToTargetStage(-2650,0.8);
                break;
            }
        }
        rclaw.setPosition(RDOWN);
    }
    public void pickup() {
        moveToTargetrStage(300, 0.8);
        runtime.reset();
        while (claw.getPosition() != CLAW_CLOSE) {
            if (runtime.seconds() > 1) {
                claw.setPosition(CLAW_CLOSE);
                break;
            }
        }
        runtime.reset();
        while (rslider.getCurrentPosition() != 5000000) {
            if (runtime.seconds() > 0.5) {
                moveToTargetrStage(373,0.8);
                break;
            }
        }
        runtime.reset();
        while (slider.getCurrentPosition() != 5000000) {
            if (runtime.seconds() > 0.5) {
                moveToTargetStage(-10,1.0);
                break;
            }
        }
    }
    public void basket() {
        moveToTargetrStage(1365, 0.5);
        runtime.reset();
        while (slider.getCurrentPosition() != 5000000) {
            if (runtime.seconds() > 1.3) {
                moveToTargetStage(-5920,1.0);
                break;
            }
        }
        while (rclaw.getPosition() != RUP) {
            if (runtime.seconds() > 2.8) {
                rclaw.setPosition(RUP);
                break;
            }
        }
    }

    public void extend(){
        moveToTargetrStage(350, 0.8);
        runtime.reset();
        while (slider.getCurrentPosition() != 5000000) {
            if (runtime.seconds() > 0.5) {
                moveToTargetStage(-5490,1.0);
                break;
            }
        }
        rclaw.setPosition(RDOWN);
    }
    public void stagehome(){
        moveToTargetStage(-10,1.0);
        runtime.reset();
        while (rslider.getCurrentPosition() != 5000000) {
            if (runtime.seconds() > 3) {
                moveToTargetrStage(373,0.4);
                break;
            }
        }
        rclaw.setPosition(RUP);
    }
    public void testhigh() {
        moveToTargetrStage(rslider.getCurrentPosition() + 10, 1);
    }

    public void testlow() {
        moveToTargetrStage(rslider.getCurrentPosition() - 10, 1);
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
//        runtime.reset();
//        while (rclaw.getPosition() != RDOWN) {
//            if (runtime.seconds() > 1) {
//                rclaw.setPosition(RDOWN);
//                break;
//            }
//        }


