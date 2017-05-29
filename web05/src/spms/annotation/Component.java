package spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//애노테이션 유지 정책
//애노테이션 정보를 언제까지 유지할 것인지 설정하는 문법이다.
@Retention(RetentionPolicy.RUNTIME)
//애노테이션 문법은 인터페이스 문법과 비슷하다. interface 키워드 앞에 @가 붙는다.
public @interface Component {
	//객체 이름을 저장하는 용도로 사용할 'value'라는 기본 속성을 정의합니다. value 속성은 값을 설정할 때 이름을 생략할 수 있다.
	//인터페이스의 메서드와 달리 '기본값'을 지정할 수 있다. 속성 선언 다음에 오는 'default'키워드가 기본값을 지정하는 문법이다.
	//value 속성의 값을 지정하지 않으면 default로 지정한 값이 할당 된다.
	String value() default "";
}
