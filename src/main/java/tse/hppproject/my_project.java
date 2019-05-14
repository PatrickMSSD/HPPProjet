package tse.hppproject;

public class my_project {
	
	public static void main(String[] args) {
		
		Post pst = new Post("2010-02-01T05:12:32.921+0000|1039993|3981||Lei Liu");
		System.out.println(pst.getTs());
		System.out.println(pst.getUser());
		System.out.println(pst.getUser_id());
		System.out.println(pst.getPost_id());
	}


}
