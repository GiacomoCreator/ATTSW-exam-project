package com.spring.morganti.giacomo.attsw.app.model;

import java.util.Objects;

public class Supermarket {

		private Long id;
		private String name;
		private String address;
		
		public Supermarket (Long id, String name, String address) {
			this.id = id;
			this.name = name;
			this.address = address;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		@Override
			public int hashCode() {
				return Objects.hash(id, name, address);
			}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Supermarket other = (Supermarket) obj;
			return Objects.equals(id, other.id) && Objects.equals(name, other.name) && address == other.address;
		}

		@Override
		public String toString() {
			return "Supermarket [id=" + id + ", name=" + name + ", address=" + address + "]";
		}
	
}
