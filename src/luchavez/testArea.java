package luchavez;

import java.util.ArrayList;

public class testArea {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		ArrayList a = new ArrayList<ArrayList<Object>>();
		a.add(new ArrayList<Object>());
		System.out.println(a.size());
		System.out.println(a.get(0).size());
	}
}
