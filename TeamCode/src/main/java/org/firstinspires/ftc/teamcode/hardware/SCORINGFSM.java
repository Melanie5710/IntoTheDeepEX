package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SCORINGFSM extends Mechanism {
    LIFTFSM lift = new LIFTFSM();
    ARMFSM arm = new ARMFSM();
    ElapsedTime timer = new ElapsedTime();
    public enum states {
        down,
        readyH,
        readyM,
        readyL,
        score
    }
    public states scoreStates;
    @Override
    public void init(HardwareMap hwMap) {
        scoreStates = states.down;
        lift.init(hwMap);
        arm.init(hwMap);
    }
    public void init(HardwareMap hwMap, Telemetry tele) {
        lift.init(hwMap, tele);
        arm.init(hwMap, tele);
    }
    public void loop() {
        switch(scoreStates) {
            case down:
                if(timer.milliseconds() >= 150) {
                    lift.goLow();
                    arm.down();
                }
                break;
            case readyH:
                lift.goHigh();
                arm.ready();
                break;
            case readyM:
                lift.goMid();
                arm.ready();
                break;
            case readyL:
                lift.goLow();
                arm.ready();
                break;
            case score:
                timer.reset();
                arm.dump();
                scoreStates = states.down;
                break;
        }
        lift.loop();
        arm.loop();
    }
    public void highGoal() {
        scoreStates = states.readyH;
    }
    public void midGoal() {
        scoreStates = states.readyM;
    }
    public void lowGoal() {
        scoreStates = states.readyL;
    }
    public void score() {
        scoreStates = states.score;
    }
    public void down() {scoreStates = states.down;}
}
