package atc_project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ATC{
	User user;
	Connection con;
	
	public ATC( User user, Connection con) {
		this.con  = con;
		this.user = user;
	}
	
	private void relationalQueryLogger(int query_id)throws SQLException {
		String user_query = "insert into user_query value"
				+ "(" + user.getEmployee_id()+","+query_id+ ");";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(user_query);
		if(flag>0) {
			return;
		}
		System.out.println("Query relation could not be created");
	}
	
	private void queryLogger(String query) throws SQLException {
		String table_query = "insert into query_log (query) value"
					+ "(" + "'"+ query + "');";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(table_query);
		if(flag>0) {
			String get_query_id = "select query_id from query_log where query =" +"'"+ query + "'"
					+ "order by query_id desc;";
			ResultSet rs = st.executeQuery(get_query_id);
		
			if(rs.next()) {
				relationalQueryLogger(Integer.parseInt(rs.getString(1)));
				return;
			}
		}
		System.out.println("failed to log query");
		return;
	}
	
//	private void establisheUserFlightRelation(String emp_id, String flightNumber) throws SQLException {
//		String query = "insert into flight_user value (" + emp_id + ", '" + flightNumber + "');";
//		Statement st = con.createStatement();
//		int flag = st.executeUpdate(query);
//		return;
//	}
	
	public boolean addToSchedule(String flightNumber,String Origin,String arrival,String departure,String capacity, String emp_id, String airline) throws SQLException {
		String query;
		Statement st = con.createStatement();
		if(arrival.equals("NULL")) {
			query = "insert into flight_schedule (flight_number, origin, scheduled_arrival, scheduled_departure, capacity, emp_id, airline) value ('"+flightNumber+"','"+Origin+"',"+"NULL"+",'"+departure+"',"+capacity+ "," + emp_id + ",'" + airline +"');";
		}
		else if(departure.equals("NULL")) {
			query = "insert into flight_schedule (flight_number, origin, scheduled_arrival, scheduled_departure, capacity, emp_id, airline) value ('"+flightNumber+"','"+Origin+"','"+arrival+"',"+"NULL"+","+capacity+ "," + emp_id + ",'" + airline +"');";
		}
		
		else {
			query  = "insert into flight_schedule (flight_number, origin, scheduled_arrival, scheduled_departure, capacity, emp_id, airline) value ('"+flightNumber+"','"+Origin+"','"+arrival+"','"+departure+"',"+capacity+ "," + emp_id + ",'" + airline +"');";
		}
		
		int flag = st.executeUpdate(query);
		if(flag>0) {
//			this.establisheUserFlightRelation(emp_id, flightNumber);
			String log = "FLIGHT NUMBER: "+flightNumber+" ADDED SUCCESSFULLY";
			queryLogger(log);
			return true;
		}
		return false;
}

	public boolean removeFromSchedule(String flightNumber) throws SQLException {
		Statement st = con.createStatement();
		String query = "delete from flight_schedule where flight_number = '" +flightNumber+"';";
		int flag = st.executeUpdate(query);
		if(flag>0) {
			String log = "FLIGHT NUMBER: "+flightNumber+" REMOVED SUCCESSFULLY";
			queryLogger(log);
			return true;
		}
		return false;
	}
	
	public boolean airlineExistence(String airline) throws SQLException{
		String query = "select * from airline_details where airline = '" + airline + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean scheduledPlaneExistence(String flightNumber) throws SQLException {
		String query = "select * from flight_schedule where flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(!rs.next()) {
			return false;
		}
		return true;
	}
	
	public boolean scheduledFlightUserVerification(String flightNumber) throws SQLException{
		String query = "select * from flight_schedule where flight_number = '" + flightNumber + "' and emp_id = " + user.getEmployee_id() + ";";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(!rs.next()) {
			return false;
		}
		return true;
	}
	
	public boolean hasPlaneAlreadyLanded(String flightNumber) throws SQLException{
		String query = "select isLanded from flight_schedule where flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			if(rs.getString(1).equals("Yes")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean landPlane(String flightNumber, String isTransit, String chosen_runway)throws SQLException{
		String query = "insert into current_plane(flight_number,isTransit, emp_id) value ('"+flightNumber+"','"+isTransit+"'," + user.getEmployee_id() +");" ;
		Statement st = con.createStatement();
		int flag = st.executeUpdate(query);
		if(!(flag > 0)) {
			return false;
		}
		if (flag >0) {
			String log = "FLIGHT NUMBER: " + flightNumber + " LANDED SUCCESSFULLY ON RUNWAY: " + chosen_runway + " (EMP_ID: "+ user.getEmployee_id() + ")";
			queryLogger(log);
			return true;
		}
		return false;
	}
	
	public ResultSet runwayAvailabilty()throws SQLException {
		String query = "select rn.runway_code, rn.runway_status from runway rn "
				+ "join user_runway ur on ur.runway_code = rn.runway_code "
				+ "where ur.emp_id = " + user.getEmployee_id() + " and rn.runway_status = 'Free';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

	public String updateFlightVitals(String flightNumber, String flightVital) throws SQLException {
		String result = "";
		if(flightVital.equals("Fuel")) {
			result = updateIsFueled(flightNumber);
		}
		else if(flightVital.equals("Cargo")) {
			result = updateIsCargoLoaded(flightNumber);
		}
		else if(flightVital.equals("Safety Check")) {
			result = updateIsSafetyCheck(flightNumber);
		}
		return result;
	}

	private String updateIsFueled(String flightNumber) throws SQLException {
		String query = "select uv.status from utility_vehicles uv "
				+ "	join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ " join user_runway ur on ur.runway_code = rv.runway_code "
				+ " where ur.emp_id = " + user.getEmployee_id()+ " and uv.vehicle_type = 'Fuel_Bowser';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int iterations = 0;
		int freeFlag = 0;
		while(rs.next()) {
			iterations++;
			if(rs.getString(1).equals("Free")) {
				freeFlag = 1;
				break;
			}
		}
		if(iterations == 0) {
			return "NO RUNWAY FOUND";
		}
		if(freeFlag == 0) {
			return "NO FREE FUEL BOWSER";
		}
		String fueling_query = "update current_plane set isFueled = 'Yes' where flight_number = '" + flightNumber +"';";
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String log = "FUELING OF FLIGHT NUMBER: " + flightNumber +" COMPLETED";
			queryLogger(log);
			return "SUCCESS";
		}
		return "FUELING FAILED";
	}

	private String updateIsSafetyCheck(String flightNumber) throws SQLException {
		String query = "select uv.status from utility_vehicles uv "
				+ "	join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "    join user_runway ur on ur.runway_code = rv.runway_code "
				+ "    where ur.emp_id = " + user.getEmployee_id()+ " and uv.vehicle_type = 'Safety_Check_Rig';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int iterations = 0;
		int freeFlag = 0;
		while(rs.next()) {
			iterations++;
			if(rs.getString(1).equals("Free")) {
				freeFlag = 1;
				break;
			}
		}
		if(iterations == 0) {
			return "NO RUNWAY FOUND";
		}
		if(freeFlag == 0) {
			return "NO FREE SAFETY RIGS";
		}
		String fueling_query = "update current_plane set isSafetyCheck = 'Yes' where flight_number = '" + flightNumber +"';";
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String log = "SAFTEY CHECK OF FLIGHT NUMBER: " + flightNumber +" COMPLETED";
			queryLogger(log);
			return "SUCCESS";
		}
		return "SAFETY CHECK FAILED";
}

	private String updateIsCargoLoaded(String flightNumber) throws SQLException {
		String query = "select uv.status from utility_vehicles uv "
				+ "	join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ " join user_runway ur on ur.runway_code = rv.runway_code "
				+ " where ur.emp_id = " + user.getEmployee_id()+ " and uv.vehicle_type = 'Cargo_Loader';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int iterations = 0;
		int freeFlag = 0;
		while(rs.next()) {
			iterations++;
			if(rs.getString(1).equals("Free")) {
				freeFlag = 1;
				break;
			}
		}
		if(iterations == 0) {
			return "NO RUNWAY FOUND";
		}
		if(freeFlag == 0) {
			return "NO FREE CARGO LOADERS";
		}
		String fueling_query = "update current_plane set isCargoLoaded = 'Yes' where flight_number = '" + flightNumber +"';";
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String log = "CARGO OF FLIGHT NUMBER: " + flightNumber +" LOADED";
			queryLogger(log);
			return "SUCCESS";
		}
		return "CARGO LOADING FAILED";
	}
	
	public boolean updateUtilityVehicles(String runwayCode, String vehicle, String updatedStatus) throws SQLException {
		if(vehicle.equals("Fire_Truck")) {
			return updateFireTruck(runwayCode, updatedStatus);
		}
		else if(vehicle.equals("Cargo_Loader")) {
			return updateCargoLoader(runwayCode, updatedStatus);
		}
		else if(vehicle.equals("Ambulance")) {
			return updateAmbulance(runwayCode, updatedStatus);
		}
		else if(vehicle.equals("Fuel_Bowser")) {
			return updateFuelBowser(runwayCode, updatedStatus);
		}
		else if(vehicle.equals("Refueler_Jet")) {
			return updateRefuelerJet(runwayCode, updatedStatus);
		}
		else if(vehicle.equals("Safety_Check_Rig")){
			return updateSafetyCheckRig(runwayCode, updatedStatus);
		}
		return false;
	}
	
	private boolean updateFireTruck(String runwayCode, String updatedStatus) throws SQLException {
		String fueling_query = "update utility_vehicles uv "
				+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "set status = '" + updatedStatus + "' "
				+ "where rv.runway_code = "+ runwayCode + " and uv.vehicle_type = 'Fire_Truck';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String getVehicleID = "select uv.vehicle_id from utility_vehicles uv "
					+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
					+ "where rv.runway_code = " + runwayCode +" and uv.vehicle_type = 'Fire_Truck';";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(getVehicleID);
			if(rs.next()) {
				String vehID = rs.getString(1);
				String log = "STATUS OF FIRE TRUCK(ID: " + vehID + "; RN_NO: "+ runwayCode +") CHANGED TO <" + updatedStatus + ">";
				queryLogger(log);
				return true;
			}
		}
		return false;
	}
	
	private boolean updateCargoLoader(String runwayCode, String updatedStatus) throws SQLException {
		String fueling_query = "update utility_vehicles uv "
				+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "set status = '" + updatedStatus + "' "
				+ "where rv.runway_code = "+ runwayCode + " and uv.vehicle_type = 'Cargo_Loader';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String getVehicleID = "select uv.vehicle_id from utility_vehicles uv "
					+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
					+ "where rv.runway_code = " + runwayCode +" and uv.vehicle_type = 'Cargo_Loader';";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(getVehicleID);
			if(rs.next()) {
				String vehID = rs.getString(1);
				String log = "STATUS OF CARGO LOADER(ID: " + vehID + "; RN_NO: "+ runwayCode +") CHANGED TO <" + updatedStatus + ">";
				queryLogger(log);
				return true;
			}
		}
		return false;
	}
	
	private boolean updateAmbulance(String runwayCode, String updatedStatus) throws SQLException {
		String fueling_query = "update utility_vehicles uv "
				+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "set status = '" + updatedStatus + "' "
				+ "where rv.runway_code = "+ runwayCode + " and uv.vehicle_type = 'Ambulance';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String getVehicleID = "select uv.vehicle_id from utility_vehicles uv "
					+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
					+ "where rv.runway_code = " + runwayCode +" and uv.vehicle_type = 'Ambulance';";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(getVehicleID);
			if(rs.next()) {
				String vehID = rs.getString(1);
				String log = "STATUS OF AMBULANCE(ID: " + vehID + "; RN_NO: "+ runwayCode +") CHANGED TO <" + updatedStatus + ">";
				queryLogger(log);
				return true;
			}
		}
		return false;
	}

	private boolean updateFuelBowser(String runwayCode, String updatedStatus) throws SQLException {
		String fueling_query = "update utility_vehicles uv "
				+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "set status = '" + updatedStatus + "' "
				+ "where rv.runway_code = "+ runwayCode + " and uv.vehicle_type = 'Fuel_Bowser';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String getVehicleID = "select uv.vehicle_id from utility_vehicles uv "
					+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
					+ "where rv.runway_code = " + runwayCode +" and uv.vehicle_type = 'Fuel_Bowser';";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(getVehicleID);
			if(rs.next()) {
				String vehID = rs.getString(1);
				String log = "STATUS OF FUEL BOWSER(ID: " + vehID + "; RN_NO: "+ runwayCode +") CHANGED TO <" + updatedStatus + ">";
				queryLogger(log);
				return true;
			}
		}
		return false;
	}

	private boolean updateRefuelerJet(String runwayCode, String updatedStatus) throws SQLException {
		String fueling_query = "update utility_vehicles uv "
				+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "set status = '" + updatedStatus + "' "
				+ "where rv.runway_code = "+ runwayCode + " and uv.vehicle_type = 'Refueler_Jet';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String getVehicleID = "select uv.vehicle_id from utility_vehicles uv "
					+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
					+ "where rv.runway_code = " + runwayCode +" and uv.vehicle_type = 'Refueler_Jet';";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(getVehicleID);
			if(rs.next()) {
				String vehID = rs.getString(1);
				String log = "STATUS OF REFUELER JET(ID: " + vehID + "; RN_NO: "+ runwayCode +") CHANGED TO <" + updatedStatus + ">";
				queryLogger(log);
				return true;
			}
		}
		return false;
	}

	private boolean updateSafetyCheckRig(String runwayCode, String updatedStatus) throws SQLException{	
		String fueling_query = "update utility_vehicles uv "
				+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "set status = '" + updatedStatus + "' "
				+ "where rv.runway_code = "+ runwayCode + " and uv.vehicle_type = 'Safety_Check_Rig';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(fueling_query);
		if(flag > 0) {
			String getVehicleID = "select uv.vehicle_id from utility_vehicles uv "
					+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
					+ "where rv.runway_code = " + runwayCode +" and uv.vehicle_type = 'Safety_Check_Rig';";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(getVehicleID);
			if(rs.next()) {
				String vehID = rs.getString(1);
				String log = "STATUS OF SAFETY CHECK RIG(ID: " + vehID + "; RN_NO: "+ runwayCode +") CHANGED TO <" + updatedStatus + ">";
				queryLogger(log);
				return true;
			}
		}
		return false;
	}
	
	public boolean currentPlaneExistence(String flightNumber) throws SQLException {
		String query = "select * from current_plane "
				+ "where flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(!rs.next()) {;
			return false;
		}
		return true;
	}
	
	public boolean userCurrentPlaneVerification(String flightNumber) throws SQLException{
		String query = "select * from current_plane  "
				+ "where emp_id = " + user.getEmployee_id() + " and flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(!rs.next()) {
			return false;
		}
		return true;
	}

	public boolean takeoff(String flightNumber, String chosen_runway) throws SQLException {
		String query = "update flight_schedule set isLanded = 'Yes' where flight_number = '"+flightNumber+"';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(query);
		if(flag <= 0) {
			return false;
		}
		String remove_query = "delete from current_plane where flight_number = '" + flightNumber + "';";
		st = con.createStatement();
		flag = st.executeUpdate(remove_query);
		if(flag > 0) {
			String log = "FLIGHT NUMBER: " + flightNumber + " TOOK-OFF SUCCESSFULLY FROM RUNWAY: " + chosen_runway;
			queryLogger(log);
			return true;
		}
		return false;
	}
	
	public boolean preFlightCheck(String flightNumber) throws SQLException{
		String query = "select isFueled, isCargoLoaded, isSafetyCheck from current_plane "
				+ "where flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			if(rs.getString(1).equals("Yes") && rs.getString(2).equals("Yes") && rs.getString(3).equals("Yes")) {
				return true;
			}
		}
		return false;
	}
	
	public ResultSet jetAvailabilty() throws SQLException{
		String query = "select rn.runway_code, uv.status from utility_vehicles uv "
				+ "join runway_vehicle rv on rv.vehicle_id = uv.vehicle_id "
				+ "join runway rn on rn.runway_code = rv.runway_code "
				+ "join user_runway ur on ur.runway_code = rn.runway_code "
				+ "where ur.emp_id =" + user.getEmployee_id() + " and uv.vehicle_type = 'Refueler_Jet' and rn.runway_status = 'Free';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			if(rs.getString(2).equals("Free")) {
				return rs;
			}
		}
		return null;
	}

	public void scrambleJet(String flightNumber, String runwayNumber)throws SQLException{
		updateRefuelerJet(runwayNumber, "Busy");
		String log = "RE-FUELING JET SCRAMBLED FROM RUNWAY: "+ runwayNumber +" FOR FLIGHT NUMBER: " + flightNumber;
		queryLogger(log);
		return;
	}
	
	public boolean findIsTransit(String flightNumber) throws SQLException{
		String query = "select * from flight_schedule where scheduled_departure is null and flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			return false;
		}
		return true;
	}
	
	public ResultSet getPlane(String flightNumber)throws SQLException{
		String query = "select origin from flight_schedule where flight_number ='" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getScheduledFlights() throws SQLException{
		String query = "select * from flight_schedule where isLanded = 'No';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	
	public ResultSet getMyScheduledFlights() throws SQLException{
		String query = "select * from flight_schedule "
				+ "where emp_id = " + user.getEmployee_id() + " and isLanded = 'No';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getCurrentFlights() throws SQLException{
		String query = "select * from current_plane;";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getMyCurrentFlightsYesTransit() throws SQLException{
		String query = "select * from current_plane "
				+ "where emp_id = " + user.getEmployee_id() + " and isTransit = 'Yes';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getMyCurrentFlightsNoTransit() throws SQLException{
		String query = "select * from current_plane "
				+ "where emp_id = " + user.getEmployee_id() + " and isTransit = 'No';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getCurrentFlight(String flightNumber) throws SQLException{
		String query = "select * from current_plane where flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getRunwayDetails() throws SQLException{
		String query = "select * from runway;";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getMyRunways() throws SQLException{
		String query = "select * from runway rn "
				+ "join user_runway ur on ur.runway_code = rn.runway_code "
				+ "where ur.emp_id = " + user.getEmployee_id() + ";";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getQueryLog() throws SQLException{
		String query = "select ql.query_id, u.emp_id, ql.query, ql.query_time "
				+ "from query_log ql "
				+ "join user_query uq on uq.query_id = ql.query_id "
				+ "join user_details u on u.emp_id = uq.emp_id "
				+ "order by ql.query_time DESC;";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public boolean runwayExistence(String runwayNumber) throws SQLException {
		String runway_existence = "select * from runway where runway_code = " + runwayNumber + ";";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(runway_existence);
		if(!rs.next()) {
			return false;
		}
		return true;
	}
	
	public boolean runwayVerifier(String runwayNumber) throws SQLException {
		String user_runway_verification = "select u.name, rn.runway_code, rn.runway_status "
				+ "from user_details u "
				+ "join user_runway ur on u.emp_id = ur.emp_id "
				+ "join runway rn on rn.runway_code = ur.runway_code "
				+ "where rn.runway_code =" + runwayNumber +" and u.emp_id = " + user.getEmployee_id() + ";";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(user_runway_verification);
		if(!rs.next()) {
			return false;
		}
		return true;
	}
	
	public boolean updateRunwayStatus(String runwayNumber, String updatedStatus) throws SQLException{
		String query = "update runway set runway_status = '" + updatedStatus + "' where runway_code = " + runwayNumber + ";";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(query);
		if(flag > 0) {
			String log = "STATUS OF RUNWAY " + runwayNumber + " CHANGED TO <" + updatedStatus + ">";
			this.queryLogger(log);
			return true;
		}
		return false;
	}
	
	public ResultSet getRunwayAvailabilty(String runwayNumber) throws SQLException{
		String query = "select runway_status from runway where runway_code = " + runwayNumber + ";";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public ResultSet getMyRunwayVehicles(String runwayNumber) throws SQLException{		
		String query = "select uv.vehicle_type, uv.status "
				+ "from runway r "
				+ "join runway_vehicle rv on rv.runway_code = r.runway_code "
				+ "join utility_vehicles uv on uv.vehicle_id = rv.vehicle_id "
				+ "where r.runway_code = " + runwayNumber + ";";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public boolean transitCheck(String flightNumber) throws SQLException{
		String query = "select isTransit from current_plane where flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			if(rs.getString(1).equals("Yes")) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean landCheck(String flightNumber) throws SQLException {
		String query = "select * from current_plane where flight_number = '" + flightNumber + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean AccessUpdater(String EmpID,String accLevel) throws SQLException {
		String query = "update  user_details set access_level = "+ accLevel+" where"+" emp_id = "+EmpID+";";
		Statement st = con.createStatement();
		int rs = st.executeUpdate(query);
		if(rs != 0) {
			String log = "ACCESS OF EMPLOYEE (ID :"+ EmpID+") UPDATED TO "+accLevel; 
			queryLogger(log);
			return true;
		}
		return false;	
	}
	
	public String getAirlineHQ(String airline) throws SQLException {
		String query = "select airline_headquarters from airline_details\r\n"
				+ "where airline = '"+ airline + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			String hq = rs.getString(1);
			return hq;
		}
		else return null;
	}
	
	public ResultSet getAirlineFlights(String airline) throws SQLException{
		String query = "select fs.flight_number, fs.origin from airline_details ad "
				+ "join flight_schedule fs on fs.airline = ad.airline "
				+ "where ad.airline = '" + airline +"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public boolean isFromDelhi(String flightNumber)throws SQLException{
		String query = "select * from flight_schedule where flight_number = '" + flightNumber + "' and origin = 'New Delhi'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean clearNonTransit() throws SQLException {
		String query = "delete from current_plane where isTransit = 'No';";
		Statement st = con.createStatement();
		int flag = st.executeUpdate(query);
		if(flag > 0) {
			return true;
		}
		return false;
	}
}