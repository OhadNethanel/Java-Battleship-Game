package jb;

import java.util.Scanner;

public class Battleship {

	/////////// SHOW OPPONENT BOARD USING CODE 9725 AT YOUR TURN //////////////
	/////////// USE CODE 9999 TO DEPLOY RANDOM SHIPS FOR USER WHEN PICKING UP COORDINATES //////////////

	public static void main(String[] args) {

		///// BASIC SETTINGS//////
		int maximumShips = 5; // CHOOSE AMOUNT OF SHIPS FOR EACH PLAYER
		int gameSize = 10; // game size is: gameSize x gameSize
		///// END BASIC SETTINGS//////

		int[][] usrArr = new int[gameSize][gameSize]; // initiate user GameBoard size of gameSize x gameSize
		int[][] PCArr = new int[gameSize][gameSize]; // initiate opponent GameBoard size of gameSize x gameSize

		System.out.println("*** Welcome to Battle Ships game by Ohad Nethanel *** \n \nRight now the sea is empty!\n ");
		deploy(usrArr);
		// show user GameBoard (usrArr), right now empty!

		System.out.println("\n \n *** Where do you want to deploy your " + maximumShips + " battleships? \n");
		getCoords(usrArr, maximumShips);
		// allow user to locate ships

		System.out.println("**** Computer is now deploying! ****");
		computerDeploy(PCArr, maximumShips);
		// computer randomly locating ships

		System.out.println("\n **** TIME TO START THE BATTLE **** \n ");
		userTurn(usrArr, PCArr, maximumShips, maximumShips);
		// provide userTurn function with parameters:
		// 1] user created array, 2] PC created array,
		// 3] user available ships, 4] PC available ships

	}

	public static void deploy(int usrArr[][]) {
		System.out.print("   ");
		for (int i = 0; i < usrArr.length; i++) { // count to usrArr.length
			System.out.print(i);
		}
		System.out.println("");
		for (int i = 0; i < usrArr[0].length; i++) { // vertical grid loop
			System.out.print(i + " |");
			for (int j = 0; j < usrArr.length; j++) { // horizontal grid loop
				if (usrArr[i][j] == 0) {
					System.out.print(" "); // if there is no ship
				} else if (usrArr[i][j] == 1) {
					System.out.print("@"); // if there is yes ship
				} else if (usrArr[i][j] == 2) {
					System.out.print("X"); // if ship got DESTROYED
				} else if (usrArr[i][j] == 3) {
					System.out.print("M"); // if coordinates were bombed & missed
				}
			}
			System.out.print("| " + i);
			System.out.println("");
		}
		System.out.print("   ");
		for (int i = 0; i < usrArr.length; i++) {
			System.out.print(i);
		}
		System.out.println("\n");
	}

	public static void getCoords(int usrArr[][], int maximumShips) {

		Scanner input = new Scanner(System.in);

		for (int i = 0; i < maximumShips; i++) {
			System.out.print("Enter X coordinate for your " + (i + 1) + " ship: ");
			int x = input.nextInt();
			if (x == 9999) {//code 9999 to deploy random ships for user			
				
				System.out.println("Clearing user gameboard...");
				for (int row = 0; row < usrArr.length; row++) { //clear the array
					   for (int col = 0; col < usrArr[0].length; col++) {
						   usrArr[row][col] = 0;
					   }
					}
				
				System.out.println("System will now deploy " + maximumShips + " ships randomly for you! \n");
				computerDeploy(usrArr, maximumShips);
				deploy(usrArr); // refresh grid when finished!
				break;
			} else {

				while (x > usrArr.length - 1) { // check X input
					System.out.print("**ERROR, coordinates cannot be higher than grid size: choose only 0 to "
							+ (usrArr.length - 1) + " *** \n \nEnter X coordinate for your " + (i + 1) + " ship: ");
					x = input.nextInt();
				}

				System.out.print("Enter Y coordinate for your " + (i + 1) + "  ship: ");
				int y = input.nextInt();
				while (y > usrArr[0].length - 1) { // check Y input
					System.out.print("**ERROR, coordinates cannot be higher than grid size: choose only 0 to "
							+ (usrArr.length - 1) + " *** \n \nEnter Y coordinate for your " + (i + 1) + " ship: ");

					y = input.nextInt();
				}

				System.out.println("");
				if (usrArr[x][y] == 0) { // check if coordinates were already used by user, 0 = empty
					usrArr[x][y] = 1; // declaring that there is a ship now (1 = ship)
				} else {
					System.out.print("***Error coordinate already in use!*** \n \n");
					i--;
				}
			}

			deploy(usrArr); // refresh grid when finished!
		}
	}

	public static void computerDeploy(int PCArr[][], int maximumShips) {
		int rndX = (int) (Math.random() * PCArr.length);
		int rndY = (int) (Math.random() * PCArr.length);

		for (int i = 0; i < maximumShips; i++) {
			if (PCArr[rndX][rndY] == 0) {
				PCArr[rndX][rndY] = 1;
				System.out.print("Randomly deployed " + (i + 1) + " Battleships! ");
				System.out.println("X: " + rndX + " Y: " + rndY); // Reveal computer chosen coordinates
			} else { // make sure no double deployment for PC
				rndX = (int) (Math.random() * PCArr.length);
				rndY = (int) (Math.random() * PCArr.length);
				i--;
			}
		}
		System.out.println("");
	}

	public static void userTurn(int usrArr[][], int pcArr[][], int livingUSRShips, int livingPCShips) {
		System.out.println("\n**Your Turn**");
		Scanner input = new Scanner(System.in);
		System.out.print("Enter X coordinate for your attack: ");
		int x = input.nextInt();

		// SECRET CODE 9725 TO WATCH OPPONENT BOARD
		if (x == 9725) {
			System.out.println("\n**HACKING INTO OPPONENT BOARD**");
			System.out.println("*OPS HAS " + livingPCShips + " LIVING SHIPS!! KILL THEM NOW");
			deploy(pcArr);
			System.out.println("**END HACKING INTO OPPONENT BOARD**\n ");
		}
		// END SECRET CODE TO WATCH OPPONENT BOARD

		while (x > usrArr.length - 1) { // check Y input
			System.out.print("**ERROR, coordinates cannot be higher than grid size: choose only 0 to "
					+ (usrArr.length - 1) + " *** \n \nEnter X coordinate for your attack: ");
			x = input.nextInt();
		}

		System.out.print("Enter Y coordinate for your attack: ");
		int y = input.nextInt();
		while (y > usrArr[0].length - 1) { // check Y input
			System.out.print("**ERROR, coordinates cannot be higher than grid size: choose only 0 to "
					+ (usrArr.length - 1) + " *** \n \nEnter Y for your attack: ");

			y = input.nextInt();
		}

		if (pcArr[x][y] == 1) { // if there is a ship! \like we decided that ship = 1
			pcArr[x][y] = 2; // change value to 2, hit
			System.out.println("\nGread job! you sunk PC's ship! \n ");
			livingPCShips--; // discard one of opponent ships
		} else {
			System.out.println("\nOh no! you did not hit any of pc's ships!");
		}

		System.out.println("Opponent has " + livingPCShips + " ships left alive!");
		if (livingPCShips == 0) { // if PC have 0 ships alive = you win
			deploy(pcArr); // show opponent board! (pcArr)
			System.out.println("\n*** YOU HAVE WON ***");
		} else {
			pcTurn(usrArr, pcArr, livingUSRShips, livingPCShips);
		}
	}

	public static void pcTurn(int usrArr[][], int pcArr[][], int livingUSRShips, int livingPCShips) {

		System.out.print("\n**PC Turn**: ");
		int rndX = (int) (Math.random() * usrArr.length);
		int rndY = (int) (Math.random() * usrArr.length);

		if ((usrArr[rndX][rndY] == 2) || (usrArr[rndX][rndY] == 3)) { // if coordinates already picked before
			System.out.println("re-calculaating steps (coordinates already picked before)");
			while ((usrArr[rndX][rndY] == 2) || (usrArr[rndX][rndY] == 3)) { // keep random them until you get two numbers never picked before
				rndX = (int) (Math.random() * usrArr.length);
				rndY = (int) (Math.random() * usrArr.length);
			}
		}

		if (usrArr[rndX][rndY] == 1) { // if there is a ship! - like we decided that ship = 1
			usrArr[rndX][rndY] = 2; // change value to 2, hit
			System.out.println("OH NO! PC has your ship whacked!  X: " + rndX + " Y: " + rndY + "\n");
			livingUSRShips--; // discard one of your ships
		} else if (usrArr[rndX][rndY] != 2) {
			System.out.println("Yay! PC Missed! he guessed: X: " + rndX + " Y: " + rndY);
			usrArr[rndX][rndY] = 3; // 3 to mark where you got hit
		}

		System.out.println("you have " + livingUSRShips + " ships left alive! \n \n");
		if (livingUSRShips == 0) { // if you have 0 ships alive = you lost
			deploy(usrArr); // refresh grid
			System.out.println("\n*** YOU HAVE LOST ***");
		} else { // if you have ships alive
			deploy(usrArr); // refresh grid
			userTurn(usrArr, pcArr, livingUSRShips, livingPCShips); // call user turn
		}
	}
}
