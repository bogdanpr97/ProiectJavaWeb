package proiectpao.beans;

import java.util.ArrayList;
import java.util.List;

public class Produs {
	   private String code;
	   private String name;
	   private float price;
	   private int quantity;

	private int id;
	   private List<Serviciu> listaServicii = new ArrayList<Serviciu>();
		 
	   public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	   public Produs() {
	 
	   }
	   public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	   public List<Serviciu> getListaServicii() {
		return listaServicii;
	}

	public void setListaServicii(List<Serviciu> listaServicii) {
		this.listaServicii = listaServicii;
	}

	public Produs(String code, String name, float price, int quantity) {
	       this.code = code;
	       this.name = name;
	       this.price = price;
	       this.quantity = quantity;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public void setCode(String code) {
	       this.code = code;
	   }
	 
	   public String getName() {
	       return name;
	   }
	 
	   public void setName(String name) {
	       this.name = name;
	   }
	 
	   public float getPrice() {
	       return price;
	   }
	 
	   public void setPrice(float price) {
	       this.price = price;
	   }
}
