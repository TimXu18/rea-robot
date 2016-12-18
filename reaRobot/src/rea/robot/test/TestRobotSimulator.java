package rea.robot.test;

import org.junit.Test;

import rea.robot.Robot;
import rea.robot.RobotSimulator;
import rea.robot.exception.RobotException;
import junit.framework.TestCase;

/**
 * Description: use Junit test RobotSimulator class
 * @author Tim Xu
 *
 */
public class TestRobotSimulator {
	@Test
	public void testIsValidPlaceCommandSuccess() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(true, rs.isValidPlaceCommand("PLACE 2,4,NORTH"));
	}
	
	@Test
	public void testIsValidPlaceCommandSuccessLowercase() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(true, rs.isValidPlaceCommand("place 2,4,north"));
	}
	
	@Test
	public void testIsValidPlaceCommandInvalidPlaceWord() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(false, rs.isValidPlaceCommand("PJACE 2,4,NORTH"));
	}
	
	@Test
	public void testIsValidPlaceCommandInvalidPosition() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(false, rs.isValidPlaceCommand("PLACE 5,4,NORTH"));
	}
	
	@Test
	public void testIsValidPlaceCommandInvalidFaceValue() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(false, rs.isValidPlaceCommand("PLACE 2,4,EASTSOUTH"));
	}
	
	@Test
	public void testIsValidPlaceCommandInvalidFormat() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(false, rs.isValidPlaceCommand("PLACE,2,4,EAST"));
	}
	
	@Test
	public void testIsValidPlaceCommandInvalidPositionFormat() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(false, rs.isValidPlaceCommand("PLACE 2,4,5,EAST"));
	}
	
	@Test
	public void testIsValidPlaceCommandPositionNotNumber() {
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(false, rs.isValidPlaceCommand("PLACE 2,4y,EAST"));
	}
	
	@Test
	public void testIsIntegerSuccess(){
		TestCase.assertEquals(true, RobotSimulator.isInteger("56"));
	}
	
	@Test
	public void testIsIntegerFail(){
		TestCase.assertEquals(false, RobotSimulator.isInteger("2g"));
	}
	
	@Test
	public void testGetRobotPositionAndFaceSuccess(){
		RobotSimulator rs = new RobotSimulator();
		String[] placeArr = rs.getRobotPositionAndFace("PLACE 3,1,NORTH");
		
		TestCase.assertEquals("3", placeArr[0]);
		TestCase.assertEquals("1", placeArr[1]);
		TestCase.assertEquals("NORTH", placeArr[2]);
	}
	
	@Test
	public void testGetRobotPositionAndFacePositionFailed(){
		RobotSimulator rs = new RobotSimulator();
		String[] placeArr = rs.getRobotPositionAndFace("PLACE 3,1,2,NORTH");
		
		TestCase.assertEquals(0, placeArr.length);
	}
	
	@Test
	public void testGetRobotPositionAndFaceFormatFailed(){
		RobotSimulator rs = new RobotSimulator();
		String[] placeArr = rs.getRobotPositionAndFace("PLACE:3,1,2,NORTH");
		
		TestCase.assertEquals(0, placeArr.length);
	}
	
	@Test
	public void testIsValidCommandSuccess(){
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(true, rs.isValidCommand("LEFT"));
	}
	
	@Test
	public void testIsValidCommandLowerCaseSuccess(){
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(true, rs.isValidCommand("move"));
	}
	
	@Test
	public void testIsValidCommandFailed(){
		RobotSimulator rs = new RobotSimulator();
		TestCase.assertEquals(false, rs.isValidCommand("RIGHTD"));
	}
	
	@Test
	public void testPlaceRobotSuccess(){
		try{
			RobotSimulator rs = new RobotSimulator();
			Robot robot = new Robot();
			
			rs.placeRobot("PLACE 2,4,EAST", robot);
			TestCase.assertEquals(2, robot.getRow());
			TestCase.assertEquals(4, robot.getCol());
			TestCase.assertEquals("EAST", robot.getFaceName(robot.getFace()));
		}catch(Exception e){
		}
	}
	
	@Test
	public void testPlaceRobotIntegerException(){
		try{
			RobotSimulator rs = new RobotSimulator();
			Robot robot = new Robot();
			
			rs.placeRobot("PLACE gf,4,EAST", robot);
			TestCase.assertEquals(-1, robot.getRow());
			TestCase.assertEquals(4, robot.getCol());
			TestCase.assertEquals("EAST", robot.getFaceName(robot.getFace()));
		}catch(Exception e){
			
		}
	}
}
