package annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/*@Documented
@Inherited	//阐述了某个被标注的类型是被继承的
*/
public @interface SocketService {
	public int module();
	public int cmd();
}
