package rea.robot.test;

import org.junit.Test;

import junit.framework.TestCase;
import rea.robot.Robot;
import rea.robot.exception.RobotException;

/**
 * Description: use Junit test Robot class
 * @author Tim Xu
 *
 */
public class TestRobot {
	@Test
	public void testRotateLeftSuccess() {
		try {
			Robot robot = new Robot();
			robot.setFace(robot.WEST);

			robot.rotateLeft();
			int currentFace = robot.getFace();

			if (currentFace != robot.SOUTH) {
				TestCase.fail();
			}
		} catch (Exception e) {
			System.out.println("Test rotate left exception: " + e.getMessage());
			TestCase.fail();
		}
	}

	@Test(expected = RobotException.class)
	public void testRotateLeftException() throws Exception {
		Robot robot = new Robot();
		robot.setFace(0);

		robot.rotateLeft();
		int currentFace = robot.getFace();

		if (currentFace != robot.EAST) {
			TestCase.fail();
		}
	}

	@Test
	public void testRotateRightSuccess() {
		try {
			Robot robot = new Robot();
			robot.setFace(robot.EAST);

			robot.rotateRight();
			int currentFace = robot.getFace();

			if (currentFace != robot.SOUTH) {
				TestCase.fail();
			}
		} catch (Exception e) {
			System.out
					.println("Test rotate right exception: " + e.getMessage());
		}
	}

	@Test(expected = RobotException.class)
	public void testRotateRightException() throws Exception {
		Robot robot = new Robot();
		robot.setFace(8);

		robot.rotateRight();
		int currentFace = robot.getFace();

		if (currentFace != robot.EAST) {
			TestCase.fail();
		}
	}

	@Test
	public void testMoveSuccessMoved() {
		try {
			Robot robot = new Robot();
			robot.setRow(3);
			robot.setCol(4);
			robot.setFace(robot.WEST);

			robot.move();
			String afterMoveReport = robot.report();

			TestCase.assertEquals("2,4,WEST", afterMoveReport);
		} catch (Exception e) {
		}
	}

	@Test
	public void testMoveSuccessNotMove() {
		try {
			Robot robot = new Robot();
			robot.setRow(4);
			robot.setCol(2);
			robot.setFace(robot.EAST);

			robot.move();
			String afterMoveReport = robot.report();

			TestCase.assertEquals("4,2,EAST", afterMoveReport);
		} catch (Exception e) {
		}
	}

	@Test(expected = RobotException.class)
	public void testMoveFaceException() throws Exception {
		Robot robot = new Robot();
		robot.setRow(3);
		robot.setCol(4);
		robot.setFace(7);

		robot.move();
		String afterMoveReport = robot.report();

		TestCase.assertEquals("3,4,EAST", afterMoveReport);
	}

	@Test(expected = RobotException.class)
	public void testMovePositionException() throws Exception {
		Robot robot = new Robot();
		robot.setRow(5);
		robot.setCol(6);
		robot.setFace(robot.WEST);

		robot.move();
		String afterMoveReport = robot.report();

		TestCase.assertEquals("5,6,WEST", afterMoveReport);
	}

	@Test
	public void testGetFaceNameSuccess() {
		try {
			Robot robot = new Robot();
			String faceName = robot.getFaceName(robot.SOUTH);

			TestCase.assertEquals("SOUTH", faceName);
		} catch (Exception e) {
		}
	}

	@Test(expected = RobotException.class)
	public void testGetFaceNameException() throws Exception {
		Robot robot = new Robot();
		String faceName = robot.getFaceName(9);

		TestCase.assertEquals("SOUTH", faceName);
	}

	@Test
	public void testReportSuccess() {
		try {
			Robot robot = new Robot();
			robot.setRow(1);
			robot.setCol(3);
			robot.setFace(robot.SOUTH);

			String reportStr = robot.report();
			TestCase.assertEquals("1,3,SOUTH", reportStr);
		} catch (Exception e) {
		}
	}

	@Test(expected = RobotException.class)
	public void testReportException() throws Exception {
		Robot robot = new Robot();
		robot.setRow(1);
		robot.setCol(3);
		robot.setFace(0);

		String reportStr = robot.report();
		TestCase.assertEquals("1,3,SOUTH", reportStr);
	}

	@Test
	public void isValidMoveSuccessCanNotMove() {
		Robot robot = new Robot();
		robot.setRow(1);
		robot.setCol(4);
		robot.setFace(robot.NORTH);

		TestCase.assertEquals(false, robot.isValidMove());
	}

	@Test
	public void isValidMoveSuccessCanMove() {
		Robot robot = new Robot();
		robot.setRow(4);
		robot.setCol(3);
		robot.setFace(robot.NORTH);

		TestCase.assertEquals(true, robot.isValidMove());
	}

	@Test
	public void isValidPositionSuccess() {
		Robot robot = new Robot();
		robot.setRow(4);
		robot.setCol(4);
		robot.setFace(robot.NORTH);

		TestCase.assertEquals(true, robot.isValidPosition());
	}

	@Test
	public void isValidPositionFailed() {
		Robot robot = new Robot();
		robot.setRow(6);
		robot.setCol(3);
		robot.setFace(robot.NORTH);

		TestCase.assertEquals(false, robot.isValidPosition());
	}
	
	@Test
	public void testGetFaceIdSuccess() {
		try {
			Robot robot = new Robot();
			int faceId = robot.getFaceId("WEST");

			TestCase.assertEquals(1, faceId);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testGetFaceIdSuccessLowercase() {
		try {
			Robot robot = new Robot();
			int faceId = robot.getFaceId("west");

			TestCase.assertEquals(1, faceId);
		} catch (Exception e) {
		}
	}

	@Test(expected = RobotException.class)
	public void testGetFaceIdException() throws Exception {
		Robot robot = new Robot();
		int faceId = robot.getFaceId("SOUTHWEST");

		TestCase.assertEquals(4, faceId);
	}

}
