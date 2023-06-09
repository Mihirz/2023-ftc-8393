package org.firstinspires.ftc.teamcode.autonomous.selectionPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.teamcode.autonomous.PoleFakeTroubleAuto;

@Autonomous(name="4 - Right Fake Pole Trouble")
@Disabled
public class RightFakeTrouble extends PoleFakeTroubleAuto {
    public RightFakeTrouble() {super(AutoOrientation.RIGHT);}
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
    }
}