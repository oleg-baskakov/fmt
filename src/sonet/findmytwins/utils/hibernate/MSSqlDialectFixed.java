package sonet.findmytwins.utils.hibernate;

import org.hibernate.Hibernate;

import org.hibernate.dialect.SQLServerDialect;

public class MSSqlDialectFixed  extends SQLServerDialect {
	
	  public MSSqlDialectFixed() {
		    super();
		    registerFunction("bitwise_and", new BitwiseAndFunction("bitwise_and", Hibernate.INTEGER));
		  } 

}
