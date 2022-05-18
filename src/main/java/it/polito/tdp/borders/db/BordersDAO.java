package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public Map<Integer, Country> loadAllCountries() {

		String sql = "SELECT ccode as id, StateAbb as abb, StateNme as name FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		Map<Integer, Country> mappa = new TreeMap<Integer,Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				Country c = new Country(rs.getString("name"),rs.getInt("id"),rs.getString("abb"));
				result.add(c);
				mappa.put(c.getId(), c);
			}
			
			conn.close();
			return mappa;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Country> getListaCountries(){
		

			String sql = "SELECT ccode as id, StateAbb as abb, StateNme as name FROM country ORDER BY StateAbb";
			List<Country> result = new ArrayList<Country>();
			//Map<Integer, Country> mappa = new TreeMap<Integer,Country>();
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
					Country c = new Country(rs.getString("name"),rs.getInt("id"),rs.getString("abb"));
					result.add(c);
					//mappa.put(c.getId(), c);
				}
				
				conn.close();
				return result;

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore connessione al database");
				throw new RuntimeException("Error Connection Database");
			}
		
	}
	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}
	
	
	public List<Border> getBorders(int anno, Map<Integer, Country> paesi){
		String sql = "SELECT state1ab as s1, state1no as id1, state2ab as s2, state2no as id2 "
				+ "FROM contiguity cn, country co "
				+ "WHERE co.StateAbb = cn.state1ab OR co.StateAbb = cn.state2ab and cn.conttype = 1 AND cn.year <= ? ";
		
		String sql2 = "SELECT DISTINCT c1.state1no as id1, c1.state1ab as ab1, c1.state2no as id2, c1.state2ab as ab2 "
				+ "FROM  contiguity c1, contiguity c2 "
				+ "WHERE c1.year <= ? "
				+ "	AND c1.conttype = 1 "
				+ "	AND c1.state1no = c2.state1no "
				+ "	AND c1.state2no = c2.state2no "
				+ "	AND c1.state1no < c2.state2no ";
		List<Border> result = new ArrayList<Border>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql2);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Border b = new Border(paesi.get(rs.getInt("id1")), paesi.get(rs.getInt("id2")));
				result.add(b);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}


