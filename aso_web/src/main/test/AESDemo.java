

public class AESDemo {
	
	public static void main(String[] args) {
		String text = "Fix";
		String str = AESHelper.encryptParam(text);
		System.out.println(str);

		String str1 = AESHelper.decrypt(str);
		System.out.println(str1);
	}
}
