package org.firstinspires.ftc.teamcode.robot.autoSubsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.CachingServo;

import java.util.Map;

public class AutoTurret {
    public final String     SYSTEM_NAME = "TURRET";
    public final String     LEFT_POSITION = "LEFT_STATE";
    public final String     RIGHT_POSITION = "RIGHT_STATE";
    public final String     CENTER_POSITION = "CENTER_STATE";
    public final String     RIGHT_PICKUP_AUTO = "RIGHT_PICKUP_AUTO";
    public final String     LEFT_DELIVERY_AUTO = "LEFT_DELIVERY_AUTO";
    public final String     LEFT_PICKUP_AUTO = "LEFT_PICKUP_AUTO";
    public final String     RIGHT_DELIVERY_AUTO = "RIGHT_DELIVERY_AUTO";
    public final double     LEFT_POSITION_LEFT_SERVO_VALUE = 532;
    public final double     CENTER_POSITION_LEFT_SERVO_VALUE = 1375;
    public final double     RIGHT_POSITION_LEFT_SERVO_VALUE = 2166;
    public final int        LIFT_MIN_HEIGHT_TO_MOVE_TURRET = 50;
    public final int        LIFT_MIN_HEIGHT_TO_MOVE_TURRET_IN_AUTO = 70;
    public final double TURRET_CENTER_POSITION = 0.49;

    public Telemetry telemetry;

    //not sure which is right and left
    private ServoImplEx leftTurretServo;
    private ServoImplEx rightTurretServo;
    private Map stateMap;
    private boolean isAuto;

    public AutoTurret(HardwareMap hwMap, Telemetry telemetry, Map stateMap, boolean isAuto) {
        this.telemetry = telemetry;
        this.stateMap = stateMap;
        this.isAuto = isAuto;

        leftTurretServo = new CachingServo(hwMap.get(ServoImplEx.class, "turret"));

        //used to be the guide...havent changed config yet so
        //rightTurretServo = new CachingServo(hwMap.get(ServoImplEx.class, "turretRight"));

        leftTurretServo.setPwmRange(new PwmControl.PwmRange(LEFT_POSITION_LEFT_SERVO_VALUE,  RIGHT_POSITION_LEFT_SERVO_VALUE));
        //rightTurretServo.setPwmRange(new PwmControl.PwmRange(LEFT_POSITION_LEFT_SERVO_VALUE, RIGHT_POSITION_LEFT_SERVO_VALUE));

    }

    public void setState(AutoLift lift){
        selectTransition((String) stateMap.get(SYSTEM_NAME));
    }

    public boolean isLiftTooLow(AutoLift lift) {
        if(isAuto){
            return lift.getAvgLiftPosition() < LIFT_MIN_HEIGHT_TO_MOVE_TURRET_IN_AUTO;
        } else {
            return lift.getPosition() < LIFT_MIN_HEIGHT_TO_MOVE_TURRET;
        }
    }

    public void selectTransition(String desiredLevel){
        switch(desiredLevel){
            case LEFT_POSITION:{
                transitionToPosition(0.01);
                break;
            } case CENTER_POSITION:{
                transitionToPosition(TURRET_CENTER_POSITION);
                break;
            } case RIGHT_POSITION:{
                transitionToPosition(1);
                break;
            }
            case LEFT_PICKUP_AUTO: {
                transitionToPosition(1.0);
                break;
            } case LEFT_DELIVERY_AUTO: {
                transitionToPosition(0.75);
                break;
            }
            case RIGHT_PICKUP_AUTO: {
                transitionToPosition(0);
                break;
            } case RIGHT_DELIVERY_AUTO: {
                transitionToPosition(0.25);
                break;
            }
        }
    }

    public void transitionToPosition (double position) {
        //raising heights to reach different junctions, so four values
        leftTurretServo.setPosition(position);
        //rightTurretServo.setPosition(position);
//        otherturretServo.setPosition(position);
    }

    public void centerTurret(){
        transitionToPosition(TURRET_CENTER_POSITION);
    }
}