package rea.robot;

import rea.robot.exception.RobotException;

/**
 * Description: Robot class has basic actions 
 * @author Tim Xu
 *
 */
public class Robot {
	// robot x,y,face
	private int row;
	private int col;
	private int face;

	// define four directions as integer
	public static final int WEST = 1;
	public static final int NORTH = 2;
	public static final int EAST = 3;
	public static final int SOUTH = 4;


	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	/**
	 * Description:  rotate left 90 degrees
	 * @throws RobotException
	 */
	public void rotateLeft() throws RobotException {
		switch (this.face) {
		case WEST:
			this.face = SOUTH;
			break;
		case NORTH:
			this.face = WEST;
			break;
		case EAST:
			this.face = NORTH;
			break;
		case SOUTH:
			this.face = EAST;
			break;
		default:
			throw new RobotException("Invalid robot face value " + this.face);
		}
	}

	/**
	 * Description:rotate right 90 degrees
	 * @throws RobotException
	 */
	public void rotateRight() throws RobotException {
		switch (this.face) {
		case WEST:
			this.face = NORTH;
			break;
		case NORTH:
			this.face = EAST;
			break;
		case EAST:
			this.face = SOUTH;
			break;
		case SOUTH:
			this.face = WEST;
			break;
		default:
			throw new RobotException("Invalid robot face value " + this.face);
		}
	}

	/**
	 * Description: move robot one unit toward current direction
	 * @throws RobotException
	 */
	public void move() throws RobotException {
		int currentFace = this.getFace();

		if (!this.isValidPosition()) {
			throw new RobotException("Invalid robot position, Row: " + this.row
					+ ", Col: " + this.col);
		}

		if (!this.isValidMove()) {
			return;
		}

		switch (currentFace) {
		case WEST:
			this.row--;
			break;
		case NORTH:
			this.col++;
			break;
		case EAST:
			this.row++;
			break;
		case SOUTH:
			this.col--;
			break;
		default:
			throw new RobotException("Invalid robot face value " + this.face);
		}
	}

	/**
	 * Description: check is current robot has valid position 
	 * @return boolean
	 */
	public boolean isValidPosition() {
		boolean isValidPosition = true;
		if (this.getRow() > 4 || this.getRow() < 0) {
			isValidPosition = false;
		}
		if (this.getCol() > 4 || this.getCol() < 0) {
			isValidPosition = false;
		}
		return isValidPosition;
	}

	/**
	 * Description: check is next move valid
	 * @return
	 */
	public boolean isValidMove() {
		int currentFace = this.getFace();
		boolean validMove = false;

		switch (currentFace) {
			case WEST:
				validMove = this.row == 0 ? false : true;
				break;
			case NORTH:
				validMove = this.col == 4 ? false : true;
				break;
			case EAST:
				validMove = this.row == 4 ? false : true;
				break;
			case SOUTH:
				validMove = this.col == 0 ? false : true;
				break;
		}
		return validMove;
	}

	/**
	 * Description: report current robot position and face direction
	 * @return String
	 * @throws RobotException
	 */
	public String report() throws RobotException {
		String faceStr = this.getFaceName(this.getFace());
		String reportStr = this.getRow() + "," + this.getCol() + "," + faceStr;

		return reportStr;
	}

	/**
	 * Description: get face name by face ID
	 * @param faceValue
	 * @return
	 * @throws RobotException
	 */
	public String getFaceName(int faceValue) throws RobotException {
		String faceStr = "";
		switch (faceValue) {
		case WEST:
			faceStr = "WEST";
			break;
		case NORTH:
			faceStr = "NORTH";
			break;
		case EAST:
			faceStr = "EAST";
			break;
		case SOUTH:
			faceStr = "SOUTH";
			break;
		default:
			throw new RobotException("Invalid robot face value " + this.face);
		}
		return faceStr;
	}
	
	/**
	 * Description: get face ID from face name
	 * @param faceStr
	 * @return
	 * @throws RobotException
	 */
	public int getFaceId(String faceStr) throws RobotException {
		int faceId = 0;
		
		if(faceStr.equalsIgnoreCase("WEST")){
			faceId = 1;
		}
		else if(faceStr.equalsIgnoreCase("NORTH")){
			faceId = 2;
		}
		else if(faceStr.equalsIgnoreCase("EAST")){
			faceId = 3;
		}
		else if(faceStr.equalsIgnoreCase("SOUTH")){
			faceId = 4;
		}
		else{
			throw new RobotException("Invalid robot face value " + faceStr);
		}
		return faceId;
	}

}
