package org.firstinspires.ftc.teamcode.autonomous.selectionPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.autonomous.DefensiveAuto;

@Autonomous(name = "Right Defense")
@Disabled
public class DefenseAutoRight extends DefensiveAuto {
    public DefenseAutoRight() {super(AutoOrientation.RIGHT);}

    public void runOpMode() throws InterruptedException {
        super.runOpMode();
    }
}
