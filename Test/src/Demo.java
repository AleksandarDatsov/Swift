
public class Demo {
	public static void main(String[] args) {
		int[][] arrDemo = new int[4][4];
		for (int i = 0; i < arrDemo.length; i++) {
			for (int j = 0; j < arrDemo.length; j++) {

				if (j == 3) {
					break;
				}
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
}
