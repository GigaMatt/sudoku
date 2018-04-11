public class Stack {
	private static Node top;
	private static int count;
	
	public Stack() {
		top = null;
		count = 0;
	}
	
	public static boolean isEmpty(){
		return (top ==null);
	}
	
	public void push(Object newItem){
		top = new Node(newItem, top);
		count++;
	}
	
	public static Object pop(){
		if (isEmpty()){
			System.out.println("Incomplete expression");
			return 0.0;
		}else{
			Node temp = top;
			top = top.next;
			count--;
			return temp.info;
		}
	}
	
	void popAll(){
		top = null;
		count = 0;
	}
	
	public Object peek(){
		if (isEmpty()){
			System.out.println("Trying to peek when stack is empty");
			return null;
		}else{
			return top.info;
		}
	}
	
	public int stackSize(){
		return count;
	}
}
