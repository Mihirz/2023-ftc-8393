//
//package org.firstinspires.ftc.teamcode.autonomous;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Vector2d;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.autonomous.imagecv.AprilTagDetectionPipeline;
//import org.firstinspires.ftc.teamcode.drive.DriveConstants;
//import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.robot.AutoBrainSTEMRobot;
//import org.firstinspires.ftc.teamcode.robot.Constants;
//import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
//import org.openftc.apriltag.AprilTagDetection;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class DefensiveAuto2 {
//    private final AutoOrientation side;
//
//    // Locations - For Left /////////////////////////////////////////////////////////////////////
//    private Pose2d startPosition = new Pose2d(-36, -64, Math.toRadians(-90));
//    private Vector2d initialApproach = new Vector2d(-36, 0);
//    private Pose2d highPoleDepositingPositionRight = new Pose2d(-22.35, 12, Math.toRadians(180));
//    private Pose2d highPoleDepositingPositionLeft = new Pose2d(-23.0, -12, Math.toRadians(180));
//
//
//    private Pose2d highPoleDepositingPosition;
//    private Pose2d lowPoleDepositingPosition = new Pose2d(-47.5, -11.5, Math.toRadians(0));
//    private Vector2d collectConesPosition = new Vector2d(-55.0, -12); //-55.5
//    private Pose2d depositOnHighPole1approach = new Pose2d(-35, -12, Math.toRadians(0));
//    private Pose2d depositOnHighPole1 = new Pose2d(-24, -12, Math.toRadians(0));
//    private Pose2d depositOnHighPole2 = new Pose2d(-25, -12, Math.toRadians(0));
//    private Pose2d depositPreloadForward = new Pose2d(-62, -36, Math.toRadians(100));
//    private Vector2d approachVector = new Vector2d(-60, -24);
//    private double approachHeading = Math.toRadians(90);
//    private Vector2d depositPreLoadForwardVector = new Vector2d(-57, -13.5);
//    private double depositPreLoadForwardHeading = Math.toRadians(100);
//    private String turretPickupPosition;
//    private String turretDeliveryPosition;
//
//    private String extensionDeliverySide;
//
//    private String depositingExtension;
//    private boolean LEFTSIDE;
//    private int initialTurn = -90;
//
//
//    private Pose2d parking3 = new Pose2d(-12, -12.5, startPosition.getHeading());
//    private Pose2d parking2 = new Pose2d(-36, -12.5, startPosition.getHeading());
//    private Pose2d parking1 = new Pose2d(-60, -12.5, Math.toRadians(180));
//    private Pose2d endParking;
//
//
//    private int initialTangent = -80;
//    private int initialApproachTangent = 180;
//    private int highPoleDepositingPositionTangent = 90;
//    private int depositPreloadSpline2Tangent = 125;
//    private String extensionCollectGoTo;
//    private String lowTurretDeliveryPosition;
//
//
//    // Async Vars /////////////////////////////////////////////////////////////////////
//    private boolean step1 = false;
//    private boolean step2 = false;
//    private boolean step3 = false;
//    private boolean step4 = false;
//    private boolean step4a = false;
//    private boolean step5 = false;
//    private boolean step5a = false;
//
//    private int parking;
//
//    private boolean trajectoryCalculated = false;
//
//    private ArrayList liftCollectionHeights;
//    Constants constants = new Constants();
//
//    TrajectorySequence autoTrajectorySequence;
//
//
//    // Open CV //////////////////////////////////////////////////////////////////////////
//    private ParkingLocation location = ParkingLocation.LEFT;
//    public OpenCvCamera camera;
//    AprilTagDetectionPipeline aprilTagDetectionPipeline;
//    static final double FEET_PER_METER = 3.28084;
//    public int Ending_Location = 1;
//    double fx = 578.272;
//    double fy = 1000;
//    double cx = 100;
//    double cy = 221.506;
//    double tagsize = 0.00037;
//    int LEFT = 1;
//    int MIDDLE = 2;
//    int RIGHT = 3;
//    AprilTagDetection tagOfInterest = null;
//
//
//    private enum ParkingLocation {
//        LEFT, MID, RIGHT
//    }
//    public enum AutoOrientation {
//        RIGHT, LEFT
//    }
//
//    public DefensiveAuto2(AutoOrientation side) {
//        this.side = side;
//        switch (side) {
//            case RIGHT:
//                LEFTSIDE = false;
//                initialApproach = new Vector2d(initialApproach.getX() , -initialApproach.getY());
//                initialTangent = 80;
//                initialApproachTangent = -90;
//                highPoleDepositingPositionTangent = 0;
//                depositPreloadSpline2Tangent = -115;
//                startPosition = new Pose2d(startPosition.getX(), -startPosition.getY(), Math.toRadians(90));
//                initialTurn = 90;
//                highPoleDepositingPosition = new Pose2d(highPoleDepositingPositionRight.getX(), highPoleDepositingPositionRight.getY(), Math.toRadians(180));
//                lowPoleDepositingPosition = new Pose2d(lowPoleDepositingPosition.getX(), -lowPoleDepositingPosition.getY(), Math.toRadians(180));
//                depositPreloadForward = new Pose2d(depositPreloadForward.getX(), -depositPreloadForward.getY(), -depositPreloadForward.getHeading());
//                depositPreLoadForwardVector = new Vector2d(depositPreLoadForwardVector.getX(), -depositPreLoadForwardVector.getY());
//                depositPreLoadForwardHeading = Math.toRadians(-100);
//                approachVector = new Vector2d(approachVector.getX(), -approachVector.getY());
//                approachHeading = Math.toRadians(-90);
//                collectConesPosition = new Vector2d(collectConesPosition.getX(), -collectConesPosition.getY());
//                depositOnHighPole1approach = new Pose2d(depositOnHighPole1approach.getX(), -depositOnHighPole1approach.getY(), Math.toRadians(180));
//                depositOnHighPole1 = new Pose2d(depositOnHighPole1.getX(), -depositOnHighPole1.getY(), Math.toRadians(180));
//                depositOnHighPole2 = new Pose2d(depositOnHighPole2.getX(), -depositOnHighPole2.getY(), Math.toRadians(180));
//
//
//                parking1 = new Pose2d(-12, 12.5, startPosition.getHeading());
//                parking2 = new Pose2d(-36, 12.5, startPosition.getHeading());
//                parking3 = new Pose2d(-60, 12.5, Math.toRadians(180));
//                break;
//            case LEFT:
//                highPoleDepositingPosition = new Pose2d(highPoleDepositingPositionLeft.getX(), highPoleDepositingPositionLeft.getY(), Math.toRadians(180));
//                LEFTSIDE = true;
//                initialTangent = -80;
//                initialApproachTangent = 100;
//                highPoleDepositingPositionTangent = 0;
//                depositPreloadSpline2Tangent = 25;
//                break;
//        }
//    }
//
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        // Timers ////////////////////////////////////////////////////////////////////////
//        ElapsedTime runTime = new ElapsedTime();
//        ElapsedTime totalTime = new ElapsedTime();
//
//        // Hardwhare ///////////////////////////////////////////////////////////////////
//        SampleMecanumDrive drive = new SampleMecanumDrive(this.hardwareMap);
//        this.stateMap = new HashMap<String, String>() {
//        };
//        AutoBrainSTEMRobot robot = new AutoBrainSTEMRobot(this.hardwareMap, this.telemetry, this.stateMap, true);
//
//        switch (side) {
//            case LEFT:
//                lowTurretDeliveryPosition = robot.turret.LEFT_POSITION;
//                extensionCollectGoTo = robot.arm.AUTO_EXTENSION_COLLECT_LEFT;
//                turretPickupPosition = robot.turret.LEFT_PICKUP_AUTO;
//                turretDeliveryPosition = robot.turret.RIGHT_POSITION;
//                extensionDeliverySide = robot.arm.RIGHT_SIDE_EXTENDED_AUTO;
//                endParking = new Pose2d(parking3.getX(), parking3.getY(), startPosition.getHeading());
//                break;
//            case RIGHT:
//                lowTurretDeliveryPosition = robot.turret.RIGHT_POSITION;
//                extensionCollectGoTo = robot.arm.AUTO_EXTENSION_COLLECT_RIGHT;
//                turretPickupPosition = robot.turret.RIGHT_PICKUP_AUTO;
//                turretDeliveryPosition = robot.turret.LEFT_POSITION;
//                extensionDeliverySide = robot.arm.LEFT_SIDE_EXTENDED_AUTO;
//                endParking = new Pose2d(parking1.getX(), parking1.getY(), startPosition.getHeading());
//                break;
//        }
//
//        // State Map ////////////////////////////////////////////////////////////////
//
//        this.stateMap.put(robot.turret.SYSTEM_NAME, robot.turret.CENTER_POSITION);
//
//        // Open CV //////////////////////////////////////////////////////////////////
//
//
//        drive.setPoseEstimate(startPosition);
//
//        while (!this.opModeIsActive() && !this.isStopRequested()) {
//
//            if (currentDetections.size() != 0) {
//                boolean tagFound = false;
//
//                for (AprilTagDetection tag : currentDetections) {
//
//                    if (tag.id == MIDDLE) {
//                        tagOfInterest = tag;
//                        location = ParkingLocation.MID;
//                        if (parking != 2) {
//                            trajectoryCalculated = false;
//                        }
//                        parking = 2;
//                        tagFound = true;
//                        telemetry.addData("Open CV :", "Mid");
//                        endParking = parking2;
//                        break;
//
//                    } else if (tag.id == RIGHT) {
//                        tagOfInterest = tag;
//                        location = ParkingLocation.RIGHT;
//                        if (parking != 3) {
//                            trajectoryCalculated = false;
//                        }
//                        parking = 3;
//                        tagFound = true;
//                        telemetry.addData("Open CV :", "Right");
//                        endParking = parking3;
//                        break;
//
//                    } else {
//                        tagOfInterest = tag;
//                        location = ParkingLocation.LEFT;
//                        tagFound = true;
//                        if (parking != 1) {
//                            trajectoryCalculated = false;
//                        }
//                        parking = 1;
//                        telemetry.addData("Open CV :", "Left");
//                        endParking = parking1;
//                        break;
//
//                    }
//                }
//            }
//
//            if (trajectoryCalculated == false) {
//                autoTrajectorySequence = initializeTrajectories(robot, drive);
//                trajectoryCalculated = true;
//            }
//
//            if (endParking != null) {
//                telemetry.addData("Parking", endParking);
//            }
//            telemetry.update();
//        }
//
//        this.waitForStart();
//        camera.closeCameraDevice();
//        robot.updateSystems();
//        drive.followTrajectorySequenceAsync(autoTrajectorySequence);
//
//        while (opModeIsActive()) {
//            telemetry.addData("Parking", endParking);
//            telemetry.update();
//            drive.update();
//            robot.updateSystems();
//        }
//    }
//
//    private TrajectorySequence initializeTrajectories(AutoBrainSTEMRobot robot, SampleMecanumDrive drive) {
//
//        TrajectorySequence deliverPreload = drive.trajectorySequenceBuilder(startPosition)
//                .setReversed(true)
//                .setTangent(initialTangent)
//                .UNSTABLE_addTemporalMarkerOffset(-0.1, () -> {
//                })
//                .lineToConstantHeading(initialApproach)
//                .waitSeconds(24)
//                .UNSTABLE_addTemporalMarkerOffset(-0.75, () -> {
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
//
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
//                })
//                .UNSTABLE_addTemporalMarkerOffset(18.3, () -> {
//                })
//                .UNSTABLE_addTemporalMarkerOffset(18.7, () -> {
//                })
//                .UNSTABLE_addTemporalMarkerOffset(18.74, () -> {
//                })
//
//                .UNSTABLE_addTemporalMarkerOffset(18.785, () -> {
//                })
//                .UNSTABLE_addTemporalMarkerOffset(18.9, () -> {
//
//                })
//
//                .waitSeconds(0.5)
//
//                .lineToLinearHeading(new Pose2d(collectConesPosition, Math.toRadians(180)))
//
//
//
//                .lineToLinearHeading(endParking)
//
//                .build();
//
//        return deliverPreload;
//    }
//
//    private void resetLift() {
//    }
//}