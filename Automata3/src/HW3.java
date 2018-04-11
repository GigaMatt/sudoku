/**
 * HW 9
 * By: Matthew S Montoya
 * Instructor: Dr. Vladik Krenovich
 * Last Modified: 22 March 2018
 */ 

import java.util.*;

class HW3{   
	private static Scanner input;

	public static void main (String[] args){
		getEquation();
	}
	public static void getEquation() {

		input = new Scanner(System.in);

		//get the left hand side of the equation
		System.out.println("We're going to transform an equation e.g.(1+5)/(9-8) into postfix. Only enter single-digit numbers");
		System.out.println("Please enter the first number of the left hand side of the equation.");
		int l1 = input.nextInt();
		System.out.println("Please enter the second number of the left hand side of the equation.");
		int l2 = input.nextInt();
		System.out.println("Would you like to add, subtract, multiply, or divide these numbers (+,-,*,/)?");

		//get the left hand operand
		char lOperand = input.next().charAt(0);

		//get the right hand side of the equation
		System.out.println("Please enter the first number of the right hand side of the equation.");
		int r1 = input.nextInt();
		System.out.println("Please enter the second number of the right hand side of the equation.");
		int r2 = input.nextInt();
		System.out.println("Would you like to add, subtract, multiply, or divide these numbers (+,-,*,/)?");
		//input.next();

		//get the right hand operand
		char rOperand = input.next().charAt(0);

		//get the overall operand
		System.out.println("Would you like to add, subtract, multiply, or divide this equation (+,-,*,/)?");
		char operand = input.next().charAt(0);

		String arithmeticExpression = "("+l1+lOperand+l2+")"+operand+"("+r1+rOperand+r2+")";
		System.out.println("Your operation is: "+arithmeticExpression);

		postfixStacks(arithmeticExpression);
	}


	public static void postfixStacks(String arithmeticExpression) {
		Stack postfixStack = new Stack();

		//turn string into a char array
		char[] array = arithmeticExpression.toCharArray();
		StringBuilder stringBuilder = new StringBuilder();

		//iterate through the array and push and pop into the stack
		String str;
		for(int i=0; i<array.length; i++) {
			//if last element, pop the ( )
			if(i==array.length-1) {
				//System.out.println(postfixStack.peek());
				stringBuilder.append(postfixStack.pop());
				stringBuilder.append(" ");
				System.out.println(stringBuilder.toString());
				System.out.println(postfixStack.peek());

				postfixStack.pop();
				System.out.println(postfixStack.peek());

			}

			//if array[i] is a number, add to the postfix string, e.g. 10 1 -
			if(isInteger(array[i])) {
				stringBuilder.append(array[i]);
				stringBuilder.append(" ");

				//print each iteration
				System.out.println(stringBuilder.toString());
			}

			//pray this works
			else{
				str = String.valueOf(array[i]);
				//if notAnInteger, push to the stack
				if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("(")) {
					postfixStack.push(array[i]);
				}
				//if ")", then pop && push ")" into stack
				if(str.equals(")")) {
					String tempVarFromStack = String.valueOf(postfixStack.peek());
					stringBuilder.append(tempVarFromStack);
					stringBuilder.append(" ");

					//print each iteration
					System.out.println(stringBuilder.toString());
					postfixStack.pop();
					postfixStack.push(array[i]);
				}
			}
		}
		String finalString = stringBuilder.toString();
		System.out.println("Your postfix form is: "+finalString);

		//print each iteration
		computePostfix(finalString);
	}


	private static void computePostfix(String finalString) {

		Stack computeStack = new Stack();
		String str, popped;
		int x,y;
		int leftResult = 0, rightResult = 0, result = 0;
		char[] postfixArray = finalString.toCharArray();

		int counter = 0;
		for(int i=0; i<postfixArray.length; i++) {
			if(isInteger(postfixArray[i])) {
				computeStack.push(postfixArray[i]);
				counter++;
			}
			else {
				str = String.valueOf(postfixArray[i]);
				if(!(i==postfixArray.length-1)) {
					if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
						popped = (String.valueOf(computeStack.pop()));
						y = Integer.parseInt(popped);
						popped = (String.valueOf(computeStack.pop()));
						x = Integer.parseInt(popped);
						if(counter<3) {
							if(str.equals("+"))
								leftResult+= (x+y);
							if(str.equals("-"))
								leftResult-= (x-y);
							if(str.equals("*"))
								leftResult*= (x*y);
							if(str.equals("/"))
								leftResult/= (x/y);
						}
						if(counter>3) {
							if(str.equals("+"))
								rightResult+= (x+y);
							if(str.equals("-"))
								rightResult-= (x-y);
							if(str.equals("*"))
								rightResult*= (x*y);
							if(str.equals("/"))
								rightResult/= (x/y);
						}
					}
				}
				if(i==postfixArray.length-1) {
					if(str.equals("+"))
						result+= (leftResult+rightResult);
					if(str.equals("-"))
						result-= (leftResult-rightResult);
					if(str.equals("*"))
						result*= (leftResult*rightResult);
					if(str.equals("/"))
						result/= (leftResult/rightResult);
				}

			}
		}
		System.out.println("Your result is: "+result);
	}


	static boolean isInteger(char c) {
		String str = String.valueOf(c);
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}