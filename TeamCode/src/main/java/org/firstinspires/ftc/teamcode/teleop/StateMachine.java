package org.firstinspires.ftc.teamcode.teleop;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import android.os.SystemClock;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
public class StateMachine {
    public static final double OPEN = 0.425;
    public static final double CLOSE = 0.34;
    protected HardwareMap hwMap;

    public StateMachine(HardwareMap hwMap) {
        this.hwMap = hwMap;
    }

    public enum LiftState {
        STRAIGHTFORWARD,
        UPWARD,
        TESTHIGH,
        TESTLOW,
        TESTHIGHS,
        TESTLOWS,
        ARMUP,
        ARMDOWN,
        LEVEL1,
        STAGEHOME,
        DROPOFF
    }

    public void setState(LiftState state) {
        this.liftState = state;
    }

    public LiftState getState() {
        return this.liftState;
    }

    private LiftState liftState;
    //    private Servo claw, rclaw, hrclaw;
    private DcMotor frontLeft, frontRight, backLeft, backRight, slider, rslider;

    public void init() {
        frontLeft = hwMap.get(DcMotorEx.class, "leftFront");
        frontRight = hwMap.get(DcMotorEx.class, "rightFront");
        backLeft = hwMap.get(DcMotorEx.class, "leftBack");
        backRight = hwMap.get(DcMotorEx.class, "rightBack");
        slider = hwMap.get(DcMotorEx.class, "slider");
        rslider = hwMap.get(DcMotorEx.class, "rslider");
//        claw = hwMap.get(Servo.class, "claw");
//        rclaw = hwMap.get(Servo.class, "rclaw");
//        hrclaw = hwMap.get(Servo.class, "hrclaw");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftState = LiftState.STAGEHOME;

    }

    public void runState(LiftState previousState) {
        switch (liftState) {
            case STAGEHOME:
                stagehome();
                break;
            case UPWARD:
                upward();
                break;
            case STRAIGHTFORWARD:
                straightforward();
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
            default:
                break;
        }
    }
    public void straightforward() {
        moveToTargetrStage(104, 0.05);
    }
    public void upward() {
        moveToTargetrStage(440, 0.05);
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
        public void stagehome(){
        moveToTargetrStage(10, 0.3);
    }
    public void testhigh() {
        moveToTargetrStage(rslider.getCurrentPosition() - 10, 1);
    }

    public void testlow() {
        moveToTargetrStage(rslider.getCurrentPosition() + 10, 1);
    }

    public void testhighs() {
        moveToTargetStage(slider.getCurrentPosition() - 10, 1);
    }

    public void testlows() {
        moveToTargetStage(slider.getCurrentPosition() + 10, 1);
    }
}
//462
//104
//5

//    public void dropoff(LiftState previousState){
////        if (previousState == LiftState.ARMUP ||  previousState == LiftState.ARMDOWN) {
////            return;
////        }
//        dropBox.setPosition(0.45);
//    }
//    public void stagehome(){
//        moveToTargetStage(1, 0.7);
//        dropBox.setPosition(1.0);
//    }
//    //stage1:
//    //stage2:
//    public void level1(){
//        moveToTargetStage(-480,0.5);
//    }
//    //stage1:
//    //stage2:
//    public void sliderup(){
//        moveToTargetStage(Slider.getCurrentPosition() - 330,0.5);
//
//    }
//
////    public void level3(){
////        moveToTargetStage();
////    }

