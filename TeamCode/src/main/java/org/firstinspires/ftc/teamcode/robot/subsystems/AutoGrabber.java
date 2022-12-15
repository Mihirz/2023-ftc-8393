package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.robot.Constants;
import org.firstinspires.ftc.teamcode.robot.Subsystem;
import org.firstinspires.ftc.teamcode.util.CachingServo;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import java.util.Map;


public class AutoGrabber implements Subsystem {
    private Telemetry telemetry;

    public ServoImplEx grabber;


    public final String SYSTEM_NAME = "GRABBER";
    public final String OPEN_STATE = "OPEN";
    public final String FULLY_OPEN = "FULLYOPEN";
    public final String CLOSED_STATE = "CLOSED";
    Constants constants = new Constants();

    public final double REGULAR_OPEN = 900;
    public final double CLOSED_VALUE = 1850;

    private Map stateMap;

    private boolean isAuto;

    public AutoGrabber(HardwareMap hwMap, Telemetry telemetry, Map stateMap, boolean isAuto) {
        this.telemetry = telemetry;
        this.stateMap = stateMap;
        this.isAuto = isAuto;


        grabber = new CachingServo(hwMap.get(ServoImplEx.class, "grabber"));

        grabber.setPwmRange(new PwmControl.PwmRange(REGULAR_OPEN, CLOSED_VALUE));
        stateMap.put(constants.GRABBER_MODE, constants.DEPOSITING);

    }
    @Override
    public void reset() {

    }

    @Override
    public void update() {

    }

    @Override
    public String test() {
        return null;
    }

    public void setState(AutoLift lift) {
        if (stateMap.get(SYSTEM_NAME) == CLOSED_STATE) {
            close();
        } else if (stateMap.get(SYSTEM_NAME) == OPEN_STATE) {
            open();
        } else if (stateMap.get(SYSTEM_NAME) == FULLY_OPEN) {
            maxOpen();
        }
    }

    public void open() {
        grabber.setPosition(0.525);
    }

    public void maxOpen() {
        grabber.setPosition(0.0);
    }

    public void close() {
        grabber.setPosition(1.0);
    }
}