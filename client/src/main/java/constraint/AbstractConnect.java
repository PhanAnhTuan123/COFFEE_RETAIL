package constraint;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractConnect {
	protected void close(PreparedStatement stm) {
		// TODO Auto-generated method stub
				if(stm!=null) {
					try {
						stm.close();
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
	}
	
}
