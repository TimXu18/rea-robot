package rea.robot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import rea.robot.exception.RobotException;

/**
 * Description: Robot simulator class, 
 * use to start application and preform action from user input command
 * @author Tim Xu
 *
 */
public class RobotSimulator {
	/* has robot place on table */
	private boolean robotOnTable = false;

	/**
	 * Description: check is user input command is a valid place command
	 * @param cmd
	 * @return boolean
	 */
	public boolean isValidPlaceCommand(String cmd) {
		boolean isValidPlaceCmd = true;
		String[] splitedSpaceArr = cmd.split("\\s+");

		if (splitedSpaceArr.length == 2) {
			if (!splitedSpaceArr[0].equalsIgnoreCase("PLACE")) {
				isValidPlaceCmd = false;
			}

			if (isValidPlaceCmd) {
				String[] splitedCommaArr = splitedSpaceArr[1].split("\\s*,\\s*");

				if (splitedCommaArr.length == 3) {
					int row = RobotSimulator.isInteger(splitedCommaArr[0]) ? Integer.parseInt(splitedCommaArr[0]) : -1;
					int col = RobotSimulator.isInteger(splitedCommaArr[1]) ? Integer.parseInt(splitedCommaArr[1]) : -1;
					String faceStr = splitedCommaArr[2];

					// check is position value out of bounds
					if (row > 4 || row < 0 || col > 4 || col < 0) {
						isValidPlaceCmd = false;
					}

					// check is valid face value
					List validFaceArr = Arrays.asList("WEST", "NORTH", "EAST", "SOUTH");
					if (!validFaceArr.contains(faceStr.toUpperCase())) {
						isValidPlaceCmd = false;
					}
				} else {
					isValidPlaceCmd = false;
				}
			}
		} else {
			isValidPlaceCmd = false;
		}
		return isValidPlaceCmd;
	}

	/**
	 * Description: check is string parameter can be parsed to integer
	 * @param input string
	 * @return boolean
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	/**
	 * Description: get rebot X,Y positions and face direction from place command
	 * @param placeStr
	 * @return String[]
	 */
	public String[] getRobotPositionAndFace(String placeStr) {
		String[] splitedSpaceArr = placeStr.split("\\s+");
		String[] robotPositionAndFace = {};

		if (splitedSpaceArr.length == 2) {
			String[] splitedCommaArr = splitedSpaceArr[1].split("\\s*,\\s*");

			if (splitedCommaArr.length == 3) {
				robotPositionAndFace = new String[] { splitedCommaArr[0],
						splitedCommaArr[1], splitedCommaArr[2] };
			}
		}

		return robotPositionAndFace;
	}

	/**
	 * Description: is user input a valid command
	 * @param cmd
	 * @return boolean
	 */
	public boolean isValidCommand(String cmd) {
		boolean isValidCmd = false;
		List validOtherCmd = Arrays.asList("MOVE", "LEFT", "RIGHT", "REPORT");

		if (validOtherCmd.contains(cmd.toUpperCase())) {
			isValidCmd = true;
		}

		if (isValidPlaceCommand(cmd)) {
			isValidCmd = true;
		}
		return isValidCmd;
	}

	/**
	 * Desciption: perform actions from user input command
	 * @param cmd
	 * @param robot
	 */
	public void performAction(String cmd, Robot robot) {
		try {
			if (cmd.equalsIgnoreCase("MOVE")) {
				robot.move();
			} else if (cmd.equalsIgnoreCase("LEFT")) {
				robot.rotateLeft();
			} else if (cmd.equalsIgnoreCase("RIGHT")) {
				robot.rotateRight();
			} else if (cmd.equalsIgnoreCase("REPORT")) {
				String reportStr = robot.report();
				System.out.println("Report result: " + reportStr);
			} else if (this.isValidPlaceCommand(cmd)) {
				this.setRobotOnTable(true);
				this.placeRobot(cmd, robot);
			}
		} catch (RobotException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Description: place robot to a X,Y position from place command 
	 * @param validPlaceCmd
	 * @param robot
	 */
	public void placeRobot(String validPlaceCmd, Robot robot) {
		try {
			String[] placePositionFaceArr = this
					.getRobotPositionAndFace(validPlaceCmd);
			
			if (placePositionFaceArr.length == 3) {
				int row = RobotSimulator.isInteger(placePositionFaceArr[0]) ? Integer
						.parseInt(placePositionFaceArr[0]) : -1;
				int col = RobotSimulator.isInteger(placePositionFaceArr[1]) ? Integer
						.parseInt(placePositionFaceArr[1]) : -1;

				robot.setRow(row);
				robot.setCol(col);

				int faceId = robot.getFaceId(placePositionFaceArr[2]);
				robot.setFace(faceId);
			}
		} catch (Exception e) {
			System.out.println("Place robot exception: " + e.getMessage());
		}
	}

	public boolean isRobotOnTable() {
		return robotOnTable;
	}

	public void setRobotOnTable(boolean robotOnTable) {
		this.robotOnTable = robotOnTable;
	}

	public static void main(String args[]) throws Exception {
		RobotSimulator rs = new RobotSimulator();
		Robot robot = new Robot();

		// use InputStream get user input from console
		InputStream in = System.in;
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(reader);

		String inputStr = "";
		while (true) {
			System.out.println("Please enter your command:");
			inputStr = br.readLine();

			if ("exit".equalsIgnoreCase(inputStr)) {
				System.out.println("Bye");
				break;
			}

			// if robot not on table yet
			if (!rs.isRobotOnTable()) {
				if (rs.isValidPlaceCommand(inputStr)) {
					rs.setRobotOnTable(true);
					rs.placeRobot(inputStr, robot);
				} else {
					System.out.println("Please enter a valid PLACE command to place robot on table");
				}
			} 
			// if robot has placed on table, use different command
			else {
				if (rs.isValidCommand(inputStr)) {
					rs.performAction(inputStr, robot);
				} else {
					System.out.println("Please enter a valid command");
				}
			}
		}
	}
}
