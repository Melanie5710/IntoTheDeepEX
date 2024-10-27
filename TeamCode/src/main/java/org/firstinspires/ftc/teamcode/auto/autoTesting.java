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

        rclaw.setPosition(0.87);
        waitForStart();

        frontLeftMove(1000, 0.1);
        frontRightMove(1000, 0.1);
        backLeftMove(1000, 0.1);
        backRightMove(1000, 0.1);
        wait(5);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void backRightMove(int target, double power){
        backRight.setPower(power);
        backRight.setTargetPosition(target);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void backLeftMove(int target, double power){
        backLeft.setPower(power);
        backLeft.setTargetPosition(target);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void frontLeftMove(int target, double power){
        frontLeft.setPower(power);
        frontLeft.setTargetPosition(target);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void frontRightMove(int target, double power){
        frontRight.setPower(power);
        frontRight.setTargetPosition(target);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

        public void wait(int timeoutS){
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
    }


