package sonet.findmytwins.utils.hibernate;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class BitwiseAndFunction extends StandardSQLFunction  implements SQLFunction {

  public BitwiseAndFunction(String name) {
    super(name);
  }

  public BitwiseAndFunction(String name, Type type) {
    super(name, type);
  }

  public String render(List args, SessionFactoryImplementor factory)
      throws QueryException {
    
    if (args.size() != 2) {
      throw new IllegalArgumentException("the function must be passed 2 arguments");
    }
    
    StringBuffer buffer = new StringBuffer(args.get(0).toString());
    buffer.append(" & ").append(args.get(1));
    return buffer.toString();
  
} 	

}
