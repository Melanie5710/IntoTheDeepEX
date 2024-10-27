/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class autoTesting extends LinearOpMode {
    private LinearOpMode linearOpMode;
    private Servo claw, hrclaw, rclaw;
    private DcMotor frontLeft, frontRight, backLeft, backRight, slider, rslider;
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
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


        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rslider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rslider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rclaw.setPosition(0.87);
        claw.setPosition(0.5);
        waitForStart();

//        frontRight.setPower(0.1);
//        encoderDrive(0.5, -2000, 2000, 2000, -2000, 8);
//        if(isStarted()) {
        encoderDrive(0.5, 100, 100, 100, 100);
        wait(0.5);
        reset();
        encoderDrive(0.5,-1550,1550,1550,-1550);
        wait(3.0);
        reset();
        encoderDrive(0.5, 900, 900, 900, 900);
        wait(2.0);
        reset();
        moveToTargetrStage(-2380,0.5);
        wait(3.0);
        moveToTargetStage(-1106, 1.0);
        hrclaw.setPosition(0.695);
        rclaw.setPosition(0.0294);
        encoderDrive(0.1, 400, 400, 400, 400);
        wait(3.0);
        reset();
        moveToTargetrStage(-2292, 0.2);
        wait(2.0);
        moveToTargetStage(-300,0.2);
        wait(2.0);
        claw.setPosition(0.63);
        wait(0.1);
        encoderDrive(0.7, -800, -800, -800, -800);
        wait(2.0);
        reset();
        moveToTargetStage(-10,1.0);
        wait(1.0);
        rclaw.setPosition(0.87);
        moveToTargetrStage(-90, 0.5);
        wait(2.0);
        encoderDrive(0.5, -600, -600, -600, -600);
        wait(2.0);
        reset();
        encoderDrive(0.7,2300, -2300,-2300, 2300);
        wait(6.0);





//        }
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void encoderDrive(double speed, int front_Left, int front_Right, int back_Left, int back_Right) {
            frontRight.setPower(speed);
            frontLeft.setPower(speed);
            backRight.setPower(speed);
            backLeft.setPower(speed);

            frontLeft.setTargetPosition(front_Left);
            frontRight.setTargetPosition(front_Right);
            backLeft.setTargetPosition(back_Left);
            backRight.setTargetPosition(back_Right);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }
        public void wait(double timeoutS){
            runtime.reset();
            while (opModeIsActive() && runtime.seconds() < timeoutS) {
                telemetry.addData("Currently at", " at %7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
                telemetry.update();
            }
        }
        public void reset(){
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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


