package sonet.findmytwins.utils.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class MySQLDialectFixed  extends MySQL5Dialect {
	
	  public MySQLDialectFixed() {
		    super();
		    registerFunction("bitwise_and", new BitwiseAndFunction("bitwise_and", Hibernate.INTEGER));
		  }
}
